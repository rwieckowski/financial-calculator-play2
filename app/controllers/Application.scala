package controllers

import org.joda.time.LocalDate
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import fincalc._

object Application extends Controller {
  val scheduleForm = Form(
    mapping(
      "principal" -> bigDecimal,
      "interestRate" -> bigDecimal,
      "start" -> jodaLocalDate,
      "nofPayments" -> number
    )(ScheduleParams.apply)(ScheduleParams.unapply))

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def form = Action {
    val data = Map("principal" -> "30000", "interestRate" -> "10", "start" -> LocalDate.now.toString, "nofPayments" -> "24")
    Ok(views.html.schedule(scheduleForm.bind(data), Nil))
  }

  def find = Action { implicit request =>
    scheduleForm.bindFromRequest.fold(
      formWithErrors => Ok(views.html.schedule(formWithErrors, Nil)),
      params => {
        val ps = findSchedule(
          Money(params.principal.toDouble),
          params.interestRate.toDouble * 0.01,
          params.start,
          Payments.equal(params.nofPayments)
        )
        Ok(views.html.schedule(scheduleForm.fill(params), ps.get))
      }
    )
  }
}

case class ScheduleParams(principal: BigDecimal, interestRate: BigDecimal, start: LocalDate, nofPayments: Int)