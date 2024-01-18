# COSC2657 - Android Development
# Pawsome application

Pawsome is an app that connects pet lovers with pet fosterers who have dogs, cats, and other adorable animals that are waiting for their forever homes. Users can book a pet for an hour or a day, enjoy the companionship of a cuddly creature and learn how to raise and live with a pet before coming to an adopted decision. Or simply having fun with a pet and going to a coffee shop with friends.

Pawsome is easy to use and has two types of accounts: customer and fosterer. As a customer, users are able to browse a list of available pets for booking, see their photos, profiles, and reviews. They can chat with the fosterer directly and ask any questions related to pets. Moreover, they can access booking history and rate experience with the pet and the agency. The fosterer account requires users to do an upgrade and its process involves a transaction API and fee. As the fosterer, users can post new pets for booking and edit their information. You can also chat with the customers and arrange the details of the booking.


## Tech Stack

**Firebase**: Authentication, Realtime Database, FireStore, Storage

**Payment process**: Stripe API

**Language**: Kotlin

**UI toolkit**: Jetpack Compose

**Dependency Injection**: Dagger-Hilt

**Map service**: Google Maps API

**Identity verification**: VNPT eKYC

## Feature Overview

Our application offers a comprehensive suite of features designed to enhance user experience. Below is a breakdown of the main and extra features available in the app:

### Main Features (9/9 completed)

- **History**: Keep track of your past activities and transactions within the app.
- **Pet Detail**: Access in-depth information about pets, including health, history, and care instructions.
- **Recommendation**: Get personalized suggestions based on your preferences and previous interactions.
- **Booking**: Easily book appointments or services directly through the app.
- **Payment**: Secure and versatile payment options for all transactions within the app.
- **Settings (UI completion)**: Customize your app experience by adjusting the settings to your liking.
- **Notification**: Stay updated with timely alerts and notifications relevant to your interests and activities.
- **Membership**: Gain access to exclusive benefits and features by becoming a member.
- **Authentication**: A secure login system that ensures the safety and privacy of your data.
- 
### Extra Features (2/2 completed)
- **ID card verification**: Additional security layer requiring ID verification for enhanced trust and safety.
- **Chat**: Communicate directly with service providers or support through the app.

## Limitations

Our application strives to provide a seamless and intuitive user experience. However, there are a few limitations that users should be aware of:

### e-KYC Limitations:

- **VNPT's API Usage**: The application utilizes VNPT's API for electronic Know Your Customer (e-KYC) processes. The free tier of this service is limited to 100 requests per month. Users intending to make more frequent use of the e-KYC feature may need to consider a subscription plan for higher usage limits.

- **ID-Card Image Correction**: To ensure accurate information extraction, the application requires users to put in additional effort to adjust and upload a clear image of their ID-Card. This might include finding good lighting, scanning the document flat, and avoiding glares.

- **Verify ID-Card Information**: Currently, the application lacks access to a citizen ID-Cards database for verification. As a result, it cannot cross-reference users' input with official records for authentication.

### Stream Chat Limitations:

- **Backend Requirements for Real-time Communication**: The Stream Chat feature of our application requires real-time communication capabilities, which are provided by a self-hosted Python backend. This setup may require additional technical knowledge to deploy and maintain.

### Permissions:

- **Manual Granting Required**: Some features of the app require permissions that need to be manually granted in the device settings. These permissions are necessary for the app to function correctly and access certain device capabilities.

We are actively working to address these limitations and hope to provide updates that enhance the application's capabilities without compromising on ease of use and security. For guidance on how to navigate these limitations, please refer to the 'Troubleshooting' section of our documentation.



## Authors

- [Nhat Bui](https://github.com/nhat117)
- [Thuc Thieu](https://github.com/ThucT4)
- [Tri Lai](https://github.com/Tri-Lai)
- [Ngan Phan](https://github.com/nganphan3903)