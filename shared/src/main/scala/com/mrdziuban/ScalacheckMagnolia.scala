package com.mrdziuban

import magnolia.{CaseClass, Magnolia, SealedTrait, Subtype}
import org.scalacheck.{Arbitrary, Gen}
import scala.language.experimental.macros

object ScalacheckMagnolia {
  type Typeclass[A] = Arbitrary[A]

  def combine[A](cc: CaseClass[Arbitrary, A]): Arbitrary[A] =
    Arbitrary(cc.parameters.zipWithIndex.foldRight(Gen.const(List[Any]())) { case ((param, idx), acc) =>
      Gen.sized { size =>
        if (size < 0) {
          for {
            head <- Gen.resize(size, Gen.lzy(param.typeclass.arbitrary))
            tail <- Gen.resize(size, Gen.lzy(acc))
          } yield head :: tail
        } else {
          val n = cc.parameters.length - (idx + 1)
          val remainder = size % (n + 1)
          val fromRemainderGen = if (remainder > 0) Gen.choose(1, n).map(r => if (r <= remainder) 1 else 0) else Gen.const(0)
          for {
            fromRemainder <- fromRemainderGen
            headSize = size / (n + 1) + fromRemainder
            head <- Gen.resize(headSize, Gen.lzy(param.typeclass.arbitrary))
            tail <- Gen.resize(size - headSize, Gen.lzy(acc))
          } yield head :: tail
        }
      }
    }.map(cc.rawConstruct(_)))

  def dispatch[A](st: SealedTrait[Arbitrary, A])(): Arbitrary[A] =
    Arbitrary(st.subtypes.toList match {
      case Nil => Gen.fail
      case stHead :: stTail =>
        def gen(head: Subtype[Typeclass, A], tail: Seq[Subtype[Typeclass, A]]): Gen[A] =
          Gen.sized { size =>
            val nextSize = (size - 1).max(0)
            tail match {
              case Nil => Gen.resize(nextSize, Gen.lzy(head.typeclass.arbitrary))
              case h :: t => Gen.frequency(
                1 -> Gen.resize(nextSize, Gen.lzy(head.typeclass.arbitrary)),
                (t.length + 1) -> Gen.resize(nextSize, gen(h, t)))
            }
          }

        gen(stHead, stTail)
    })

  implicit def deriveArbitrary[A]: Arbitrary[A] = macro Magnolia.gen[A]
}
