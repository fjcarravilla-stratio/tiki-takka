package com.stratio.tikitakka.core

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.stratio.tikitakka.common.message._
import com.stratio.tikitakka.common.model.discovery.DiscoveryAppInfo
import com.stratio.tikitakka.common.state.ApplicationState

import scalaz.Reader


class OrchestratorActor(upAndDownActorRef: ActorRef) extends Actor with ActorLogging {

  var applications: Map[String, ApplicationState] = Map.empty[String, ApplicationState]

  def addApplicationState(applicationState: DiscoveryAppInfo): Unit =
    applications = applications + (applicationState.id -> ApplicationState(applicationState))

  def removeApplicationState(applicationId: String): Option[ApplicationState] = {
    val applicationState = applications.get(applicationId)
    applications = applications - applicationId
    applicationState
  }

  // TODO manage the errors!!
  def receive: Receive = {
    case GetApplicationInfo(appId) =>
      val app = applications.get(appId)
      app.fold(
        log.info(s"Get application info: $appId, Failure"))(_ =>
        log.info(s"Get application info: $appId, Success"))
      sender ! ResponseApplicationState(app)
    case UnregisterApplication(appId) =>
      val app = removeApplicationState(appId)
      app.fold(
        log.info(s"Unregister Application: $appId, Failure"))(_ =>
        log.info(s"Unregister Application: $appId, Success"))
    case RegisterApplication(app) =>
      applications.get(app.id).fold(
        log.info(s"Register new Application: ${app.id}"))(_ =>
        log.info(s"Update an Application: ${app.id}"))
      addApplicationState(app)
  }
}

object OrchestratorActor {
  def props = Reader {
    (dependencies: Dependencies) =>
      Props(classOf[OrchestratorActor], dependencies.upAndDownActorRef)
  }
}
