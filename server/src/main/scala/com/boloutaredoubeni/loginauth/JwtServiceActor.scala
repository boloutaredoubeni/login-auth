package com.boloutaredoubeni.loginauth

import akka.actor.Actor

class JwtServiceActor extends Actor with JwtService /* with ActorLogging */ {

  def actorRefFactory = context

  def receive = runRoute(apiRoutes ~ index)

}

