// Arquivo BookingEngine.scala

import scala.collection.mutable

case class Room(id: Int, name: String, cleanupTime: Int)
case class Reservation(roomId: Int, guest: String, startTime: Long, endTime: Long)

object BookingEngine {
  val inventory: mutable.Set[Room] = mutable.Set.empty
  val reservations: mutable.Set[Reservation] = mutable.Set.empty

  def addRoom(room: Room): Unit = {
    inventory += room
  }

  def removeRoom(roomId: Int): Unit = {
    inventory.find(_.id == roomId).foreach(room => inventory -= room)
  }

  def isRoomAvailable(roomId: Int, startTime: Long): Boolean = {
    val room = inventory.find(_.id == roomId)
    val roomCleanupEndTime = startTime + room.map(_.cleanupTime).getOrElse(0) * 60 * 60 * 1000
    !reservations.exists(r =>
      r.roomId == roomId && (startTime < r.endTime && roomCleanupEndTime > r.startTime)
    )
  }

  def bookReservation(roomId: Int, guest: String, startTime: Long, endTime: Long): Boolean = {
    val room = inventory.find(_.id == roomId)
    val existingReservation = reservations.exists(r =>
      r.roomId == roomId && (startTime < r.endTime && endTime > r.startTime)
    )

    val roomAvailable = room.map(r => isRoomAvailable(r.id, startTime)).getOrElse(false)

    if (room.isDefined && !existingReservation && roomAvailable) {
      reservations += Reservation(roomId, guest, startTime, endTime)
      true
    } else {
      false
    }
  }

  def getOccupancy(date: Long): Int = {
    reservations.count(r => r.startTime <= date && date <= r.endTime)
  }
}
