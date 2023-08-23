// Implementação da Interface CLI:

// Main.scala
import BookingEngine._

object Main extends App {
  // Exemplo de uso
  val singleRoom = Room(1, "Single Room", 4)
  val doubleRoom = Room(2, "Double Room", 4)
  
  addRoom(singleRoom)
  addRoom(doubleRoom)
  
  println("Rooms in inventory:")
  inventory.foreach(room => println(s"${room.id}: ${room.name}"))

  bookReservation(1, "John", System.currentTimeMillis(), System.currentTimeMillis() + 1000 * 60 * 60)
  bookReservation(2, "Jane", System.currentTimeMillis() + 2000 * 60 * 60, System.currentTimeMillis() + 3000 * 60 * 60)

  println(s"Occupancy at ${System.currentTimeMillis()}: ${getOccupancy(System.currentTimeMillis())}")
  
  // Mais interações podem ser adicionadas aqui
}
