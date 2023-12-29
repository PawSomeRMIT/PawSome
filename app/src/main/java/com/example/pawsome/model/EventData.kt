/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 2
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/12/2023
    Last modified: 20/12/2023
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.model

data class EventData(
    val formID: String? = "",
    val tag: String = "",
    val imageUrl: String = "",
    val organizerName: String = "",
    val eventName: String = "",
    val eventTime: String = "",
    val location: String = "",   // New field
    val attendeeCount: Int = 0,
    val description: String = "normal"
)

//Place holder mock data
val sampleEvents: List<EventData> = listOf(
    EventData(
        formID = "1",
        tag = "Environment",
        imageUrl = "",
        organizerName = "Green Earth Initiative",
        eventName = "City Clean-Up Drive",
        eventTime = "2024-01-15 08:00",
        location = "New York",
        attendeeCount = 100,
        description = "Join us in making our city cleaner and greener. This event focuses on cleaning up local parks and streets."
    ),
    EventData(
        formID = "2",
        tag = "Conservation",
        imageUrl = "",
        organizerName = "Nature Conservators",
        eventName = "Wildlife Conservation Workshop",
        eventTime = "2024-02-20 10:00",
        location = "Seattle",
        attendeeCount = 80,
        description = "A workshop dedicated to wildlife conservation and protecting endangered species."
    ),
    EventData(
        formID = "3",
        tag = "Eco Awareness",
        imageUrl = "",
        organizerName = "Eco Minds",
        eventName = "Eco-Friendly Lifestyle Seminar",
        eventTime = "2024-03-22 09:00",
        location = "Miami",
        attendeeCount = 70,
        description = "Learn about sustainable living and how to incorporate eco-friendly practices into your daily life."
    ),
    EventData(
        formID = "4",
        tag = "Green Tech",
        imageUrl = "",
        organizerName = "Tech Green",
        eventName = "Green Technology Expo",
        eventTime = "2024-05-30 10:00",
        location = "San Jose",
        attendeeCount = 200,
        description = "An expo showcasing the latest in green technology, including renewable energy solutions and sustainable gadgets."
    ),
    EventData(
        formID = "5",
        tag = "Urban Gardening",
        imageUrl = "",
        organizerName = "Garden City Project",
        eventName = "Community Gardening Day",
        eventTime = "2024-06-15 08:00",
        location = "Austin",
        attendeeCount = 50,
        description = "A community event focused on urban gardening and creating green spaces in city environments."
    )
)

//Placeholder mocl data
val popularEvents: List<EventData> = listOf(
    EventData(
        formID = "6",
        tag = "Recycling",
        imageUrl = "",
        organizerName = "Eco Warriors",
        eventName = "Recycling Awareness Campaign",
        eventTime = "2024-03-05 09:00",
        location = "San Francisco",
        attendeeCount = 150,
        description = "Raise awareness about recycling and effective waste management."
    ),
    EventData(
        formID = "7",
        tag = "Renewable Energy",
        imageUrl = "",
        organizerName = "Future Energy Forum",
        eventName = "Renewable Energy Fair",
        eventTime = "2024-04-22 11:00",
        location = "Boston",
        attendeeCount = 200,
        description = "Explore the latest in renewable energy technologies and initiatives."
    ),
    EventData(
        formID = "8",
        tag = "Climate Change",
        imageUrl = "",
        organizerName = "Climate Action Network",
        eventName = "Climate Change Symposium",
        eventTime = "2024-07-19 09:00",
        location = "Denver",
        attendeeCount = 250,
        description = "Experts discuss the impacts of climate change and strategies for mitigation and adaptation."
    ),
    EventData(
        formID = "9",
        tag = "Ocean Conservation",
        imageUrl = "",
        organizerName = "Blue Seas",
        eventName = "Save Our Oceans Conference",
        eventTime = "2024-08-05 10:00",
        location = "San Diego",
        attendeeCount = 120,
        description = "An event focused on ocean conservation, addressing issues like pollution and marine life protection."
    ),
    EventData(
        formID = "10",
        tag = "Biodiversity",
        imageUrl = "",
        organizerName = "Life on Earth",
        eventName = "Biodiversity Day",
        eventTime = "2024-09-03 09:00",
        location = "Normandie",
        attendeeCount = 180,
        description = "Celebrating biodiversity with activities and talks on the importance of maintaining a diverse ecosystem."
    )
)

val nearMe: List<EventData> = listOf<EventData>(
    EventData(
        formID = "12",
        tag = "Climate Change",
        imageUrl = "",
        organizerName = "Climate Action Network",
        eventName = "Climate Change Symposium",
        eventTime = "2024-07-19 09:00",
        location = "Denver",
        attendeeCount = 250,
        description = "Experts discuss the impacts of climate change and strategies for mitigation and adaptation."
    ),
    EventData(
        formID = "13",
        tag = "Ocean Conservation",
        imageUrl = "",
        organizerName = "Blue Seas",
        eventName = "Save Our Oceans Conference",
        eventTime = "2024-08-05 10:00",
        location = "San Diego",
        attendeeCount = 120,
        description = "An event focused on ocean conservation, addressing issues like pollution and marine life protection."
    ),
    EventData(
        formID = "14",
        tag = "Biodiversity",
        imageUrl = "",
        organizerName = "Life on Earth",
        eventName = "Biodiversity Day",
        eventTime = "2024-09-03 09:00",
        location = "Yellowstone",
        attendeeCount = 180,
        description = "Celebrating biodiversity with activities and talks on the importance of maintaining a diverse ecosystem."
    )
)