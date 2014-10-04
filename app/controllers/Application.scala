package controllers

import fincalc._
import org.joda.time.LocalDate
import play.api.data.Forms._
import play.api.data._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

object Application extends Controller {
  val scheduleForm = Form(
    mapping(
      "principal" -> bigDecimal,
      "interestRate" -> bigDecimal,
      "start" -> jodaLocalDate,
      "nofPayments" -> number
    )(ScheduleParams.apply)(ScheduleParams.unapply))

  def index = Action {
    Ok(views.html.schedule())
  }

  implicit object MoneyWrites extends Writes[Money] {
    override def writes(m: Money): JsValue = Json.toJson(m.value)
  }

  implicit val paymentWrites: Writes[Payment] = (
    (JsPath \ "date").write[LocalDate] and
      (JsPath \ "amount").write[Money] and
        (JsPath \ "principal").write[Money] and
          (JsPath \ "interest").write[Money] and
            (JsPath \ "balance").write[Money]
    )(unlift(Payment.unapply))

  implicit val scheduleParamsReads: Reads[ScheduleParams] = (
    (JsPath \ "principal").read[BigDecimal] and
      (JsPath \ "interestRate").read[BigDecimal] and
      (JsPath \ "start").read[LocalDate] and
      (JsPath \ "nofPayments").read[Int]
    )(ScheduleParams.apply _)

  def schedule() = Action(parse.json) { request =>
    val params = request.body.as[ScheduleParams]
    val ps = findSchedule(
      Money(params.principal.toDouble),
      params.interestRate.toDouble * 0.01,
      params.start,
      Payments.equal(params.nofPayments)
    )
    Ok(Json.toJson(ps.get))
  }

}

case class ScheduleParams(principal: BigDecimal, interestRate: BigDecimal, start: LocalDate, nofPayments: Int)