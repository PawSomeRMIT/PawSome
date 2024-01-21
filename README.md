For best experience, please view this file with a Markdown viewer

# COSC2657 - Android Development
![Logo Pawsome](https://i.ibb.co/nPx0nJc/Capture-d-e-cran-2024-01-20-a-7-46-01-AM.png "Pawsome Logo")
## Authors
- [Nhat Bui](https://github.com/nhat117) - s3878174
- [Thuc Thieu](https://github.com/ThucT4) - s3870730
- [Tri Lai](https://github.com/Tri-Lai) - s3799602
- [Ngan Phan](https://github.com/nganphan3903) - s3914582
## Introduction
Pawsome is an app that connects pet lovers with pet fosters who have dogs, cats, and other adorable animals that are waiting for their forever homes. Users can book a pet for an hour or a day, enjoy the companionship of a cuddly creature and learn how to raise and live with a pet before coming to an adopted decision. Or simply having fun with a pet and going to a coffee shop with friends.

Pawsome is easy to use and has two types of accounts: *normal* and *subscribed*. As a normal user, they are able to browse a list of available pets (images, basic details, address and proporsed renting price) for booking. Normal users can chat with pet owners directly and ask any questions related to pets and can access booking history. Creating pet for booking requires users to upgrade their account and its process involves a transaction API and payment. As a *subscribed* user, they can post new pets for booking and edit their information. They can also chat with the customers and arrange the details of the booking.


## Tech Stack

- **Firebase**: Authentication, FireStore, Storage
- **Payment process**: Stripe API
- **Language**: Kotlin
- **Package**: Retrofit for HHTP API requests
- **UI toolkit**: Jetpack Compose
- **Dependency Injection**: Dagger-Hilt
- **Map service**: Google Maps API
- **Identity verification**: VNPT eKYC
- **Chat service**: Stream Chat

![Alt text](https://i.ibb.co/TWTxtxK/Capture-d-e-cran-2024-01-20-a-7-46-10-AM.png "Optional title")

## Feature Overview

Our application offers a comprehensive suite of features designed to enhance user experience. Below is a breakdown of the main and extra features available in the app:

### Main Features (9/9 completed)

- **History**: Keep track of your past activities and transactions within the app.
- **Pet Detail**: Access in-depth information about pets, including their photos, profiles (breed, type, color, age, description), adopting address, proposed renting price.
- **Recommendation**: Get suggestions based on your nearest current location.
- **Booking**: User can rent pets they like based on pet details.
- **Payment**: Secure and versatile payment options for all transactions within the app.
- **Settings**: User can view list of their booking and own created pets history.
- **Notification**: User will received a notification if they have successfully create a pet for booking.
- **Membership**: Gain access to exclusive benefits and features by becoming a *subscribed* user.
- **Authentication**: A secure login system that ensures the safety and privacy of your data.

### Extra Features (2/2 completed)
- **ID card verification**: Additional security layer requiring user ID card to verify user's identity to prevent pets from being stolen.
- **Chat**: Communicate directly with pet owners.

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

### No super user account:

- **No super user account**: Currently the application does not have a super user account role. This feature will be implemented in the future.

We are actively working to address these limitations and hope to provide updates that enhance the application's capabilities without compromising on ease of use and security.

## Application Flow Diagram
![Alt text](https://i.ibb.co/6FM0WCb/Capture-d-e-cran-2024-01-20-a-7-46-21-AM.png "Pawsome UI 1")

## Screenshot
![Alt text](https://i.ibb.co/7n91PG6/Capture-d-e-cran-2024-01-20-a-7-44-51-AM.png "Pawsome UI 1")
![Alt text](https://i.ibb.co/dD3WNL6/Capture-d-e-cran-2024-01-20-a-7-45-32-AM.png "Optional title")
![Alt text](https://i.ibb.co/XWWWVth/Capture-d-e-cran-2024-01-20-a-7-45-52-AM.png "Optional title")
![Alt text](https://i.ibb.co/khZjCgs/Capture-d-e-cran-2024-01-20-a-7-50-26-AM.png "Optional title")

## Application Demo Video
Application demo video can be found [here](https://rmiteduau-my.sharepoint.com/:v:/g/personal/s3870730_rmit_edu_vn/EYPXAPmDDPpNh0UhCRRsg-0BNxFU9Io--DruPORMlgpCFw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=njbIWD)

## Team Work Distribution
| Team Member  | Main Features                                                                                                       | Extra Features                     | Contribution |
|--------------|---------------------------------------------------------------------------------------------------------------------|------------------------------------|--------------|
| Nhat Bui     | Pet Detail, About Us, Notification                                                                                  |                                    | 25%          |
| Thuc Thieu   | Pet List(distance sort, search, filter type)\n, Booking feature, Navigation graphs, Backend API, Payment, Membership| ID card verification, Chat Feature | 25%          |
| Tri Lai      | Authentication, Setting UI, Adding pet, My Pets(showing, edit)                                                      |                                    | 25%          |
| Ngan Phan    | Booking History                                                                                                     |                                    | 25%          |


## References 
- [1] “Happy homes for stray, abandoned animals in Hanoi,” THE VOICE OF VIETNAM, https://vovworld.vn/en-US/sunday-show/happy-homes-for-strayabandoned-
animals-in-hanoi-1211834.vov (accessed Jan. 9, 2024).
- [2] E. Digital, “How to choose the Right Technology Stack for your mobile app,” LinkedIn, https://www.linkedin.com/pulse/how-choose-right-technology-stackyour-
mobile-app-elicit-digital (accessed Jan. 9, 2024).
- [3] “Quote from ‘Firebase authentication - google search,’” Quote from “firebase authentication - Google Search,” https://arc.net/l/quote/gtbhafdr (accessed Jan.
9, 2024).
- [4] “API reference,” Stripe API Reference, https://stripe.com/docs/api (accessed Jan. 9, 2024).
- [5] “Kotlin and Android&nbsp; :&nbsp; android developers,” Android Developers, https://developer.android.com/kotlin (accessed Jan. 9, 2024).
- [6] “Jetpack Compose UI app development toolkit,” Android Developers, https://developer.android.com/jetpack/compose (accessed Jan. 10, 2024).
- [7] Hilt, https://dagger.dev/hilt/ (accessed Jan. 11, 2024).
- [8] “Firebase realtime database,” Google, https://firebase.google.com/docs/database (accessed Jan. 11, 2024).
- [9] Google maps platform&nbsp; |&nbsp; google for developers, https://developers.google.com/maps (accessed Jan. 11, 2024).
- [10] “Nền Tảng định Danh điện TỬ (VNPT ekyc),” VNPT, https://vnpt.com.vn/doanhnghiep/san-pham-dich-vu/nen-tang-dinh-danh-dien-tu-vnpt-ekyc/ (accessed
Jan. 11, 2024).


