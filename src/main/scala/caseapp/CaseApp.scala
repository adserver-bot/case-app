package caseapp

import caseapp.core._

object CaseApp {

  def parse[T: Parser](args: Seq[String]): Either[String, (T, Seq[String])] =
    Parser[T].apply(args)

  def parseWithHelp[T](args: Seq[String])(implicit parser: Parser[T]): Either[String, (T, Boolean, Boolean, Seq[String])] = {
    implicit val parser0 = parser.withHelp

    parse[WithHelp[T]](args).right map {
      case (WithHelp(usage, help, base), rem) =>
        (base, help, usage, rem)
    }
  }

  def helpMessage[T: Messages]: String =
    Messages[T].helpMessage

  def usageMessage[T: Messages]: String =
    Messages[T].usageMessage

  def printHelp[T: Messages](err: Boolean = false): Unit =
    (if (err) Console.err else Console.out) println helpMessage[T]

  def printUsage[T: Messages](err: Boolean = false): Unit =
    (if (err) Console.err else Console.out) println usageMessage[T]

}
