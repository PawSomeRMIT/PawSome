package com.example.pawsome.model

import com.google.android.gms.maps.model.LatLng
import java.sql.Timestamp
import java.util.Random
import java.util.UUID.randomUUID

data class PetDetail(
    val petName: String,
    val petGender: String,
    val petBreed: String,
    val petAnimal:String,
    val petColor:String,
    val petStatus: String,
    val petAge: String,
    val petDescription: String,
    val bookingPricePerDay: Float,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val id: String,
    val latLng: LatLng,
    val img: String,
    val location: String,
    val ownerId: String,
    val bookingList: List<Booking> = emptyList()
)

fun generateFakePetDetail(): PetDetail {
    val random = Random()

    val petNames = listOf("Max", "Bella", "Charlie", "Lucy")
    val genders = listOf("Male", "Female")
    val breeds = listOf("Labrador", "Beagle", "Bulldog", "Poodle")
    val animals = listOf("Dog", "Cat", "Rabbit", "Parrot")
    val colors = listOf("Black", "White", "Brown", "Grey")
    val statuses = listOf("Available", "Booked")
    val ages = listOf("1 year", "2 years", "6 months", "3 years")
    val descriptions = listOf("Friendly and playful", "Calm and loving", "Active and energetic", "Shy but affectionate")
    val locations = listOf("New York", "Los Angeles", "Chicago", "Houston")

    return PetDetail(
        petName = petNames[random.nextInt(petNames.size)],
        petGender = genders[random.nextInt(genders.size)],
        petBreed = breeds[random.nextInt(breeds.size)],
        petAnimal = animals[random.nextInt(animals.size)],
        petColor = colors[random.nextInt(colors.size)],
        petStatus = statuses[random.nextInt(statuses.size)],
        petAge = ages[random.nextInt(ages.size)],
        petDescription = descriptions[random.nextInt(descriptions.size)],
        bookingPricePerDay = random.nextFloat() * 100,
        startDate = Timestamp(System.currentTimeMillis()),
        endDate = Timestamp(System.currentTimeMillis() + random.nextInt(10) * 24 * 60 * 60 * 1000L),
        id = randomUUID().toString(),
        img = "http://example.com/pet.jpg",
        location = locations[random.nextInt(locations.size)],
        ownerId = randomUUID().toString(),
        latLng = LatLng(random.nextDouble(),random.nextDouble())
    )
}