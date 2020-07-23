# scalacheck-magnolia

scalacheck-magnolia performs generic derivation of scalacheck `Arbitrary` instances for product and sum types, i.e.
case classes and ADTs, using [Magnolia](http://magnolia.work). Much of the code was inspired by
[scalacheck-shapeless](https://github.com/alexarchambault/scalacheck-shapeless/blob/master/README.md), which I've
found to work reliably, but cause huge compile time increases, especially for deeply nested case classes.

## Usage

Add to your `build.sbt`:

```scala
resolvers += Resolver.bintrayRepo("mrdziuban", "scalacheck-magnolia")
libraryDependencies += "com.mrdziuban" %% "scalacheck-magnolia" % "0.2.1"
```

This will pull in scalacheck 1.14.3 and magnolia 0.16.0.

To materialize `Arbitrary` instances for your case classes or sealed traits, import `com.mrdziuban.ScalacheckMagnolia._`.
Magnolia can derive `Arbitrary` instances for case classes

```scala
import com.mrdziuban.ScalacheckMagnolia._
import org.scalacheck.Arbitrary

case class Test(i: Int, b: Boolean, s: String)

implicitly[Arbitrary[Test]]
```

as well as for sealed traits

```scala
sealed trait Foo
case object Bar extends Foo
case class Baz(s: String) extends Foo

implicitly[Arbitrary[Foo]]
```

It can be used in scalacheck property tests like so:

```scala
import org.scalacheck.Prop.forAll

forAll((_: Test) => true /* check some properties of Test */)
```

## License

Released under the Apache 2.0 license. See the [LICENSE file](LICENSE) for more details.
