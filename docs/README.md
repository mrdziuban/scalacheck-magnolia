# scalacheck-magnolia

scalacheck-magnolia performs generic derivation of scalacheck `Arbitrary` instances for product and sum types, i.e.
case classes and ADTs, using [Magnolia](http://magnolia.work). Much of the code was inspired by
[scalacheck-shapeless](https://github.com/alexarchambault/scalacheck-shapeless/blob/master/README.md), which I've
found to work reliably, but cause huge compile time increases, especially for deeply nested case classes.

## Usage

Add to your `build.sbt`:

```scala
resolvers += "mrdziuban-maven" at "https://raw.githubusercontent.com/mrdziuban/maven-repo/master"
libraryDependencies += "com.mrdziuban" %% "scalacheck-magnolia" % "@VERSION@"
```

This will pull in scalacheck @SCALACHECK_VERSION@ and magnolia @MAGNOLIA_VERSION@.

To materialize `Arbitrary` instances for your case classes or sealed traits, import `com.mrdziuban.ScalacheckMagnolia._`.
Magnolia can derive `Arbitrary` instances for case classes

```scala mdoc:silent
import com.mrdziuban.ScalacheckMagnolia._
import org.scalacheck.Arbitrary

case class Test(i: Int, b: Boolean, s: String)

implicitly[Arbitrary[Test]]
```

as well as for sealed traits

```scala mdoc:silent
sealed trait Foo
case object Bar extends Foo
case class Baz(s: String) extends Foo

implicitly[Arbitrary[Foo]]
```

It can be used in scalacheck property tests like so:

```scala mdoc:silent
import org.scalacheck.Prop.forAll

forAll((_: Test) => true /* check some properties of Test */)
```

## License

Released under the Apache 2.0 license. See the [LICENSE file](LICENSE) for more details.
