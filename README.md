# COSC2657 - Android Development
![Logo Pawsome](https://private-user-images.githubusercontent.com/72547907/297799619-645b2ab4-6c9e-46d0-b12e-dfbe454b09b4.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjAsIm5iZiI6MTcwNTU5MjcyMCwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTk2MTktNjQ1YjJhYjQtNmM5ZS00NmQwLWIxMmUtZGZiZTQ1NGIwOWI0LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTI4MjVhMzE2OTY1NjQ5OGVhY2JlOGQ5NGYwNGIwMGIzNWQ3YWJhNjk5YjdiNmNhZjA4N2U3ZmE1YTg2ZjMyOGImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.1CxzgSNusV7c-zivQfyAro0klWDwcB1wGixD1ftm0-s "Pawsome Logo")
## Authors
- [Nhat Bui](https://github.com/nhat117) - s3878174
- [Thuc Thieu](https://github.com/ThucT4) - s3870730
- [Tri Lai](https://github.com/Tri-Lai) - s3799602
- [Ngan Phan](https://github.com/nganphan3903) - s3914582
## Introduction

Pawsome is an app that connects pet lovers with pet fosterers who have dogs, cats, and other adorable animals that are waiting for their forever homes. Users can book a pet for an hour or a day, enjoy the companionship of a cuddly creature and learn how to raise and live with a pet before coming to an adopted decision. Or simply having fun with a pet and going to a coffee shop with friends.

Pawsome is easy to use and has two types of accounts: customer and fosterer. As a customer, users are able to browse a list of available pets for booking, see their photos, profiles, and reviews. They can chat with the fosterer directly and ask any questions related to pets. Moreover, they can access booking history and rate experience with the pet and the agency. The fosterer account requires users to do an upgrade and its process involves a transaction API and fee. As the fosterer, users can post new pets for booking and edit their information. You can also chat with the customers and arrange the details of the booking.


## Tech Stack

- **Firebase**: Authentication, Realtime Database, FireStore, Storage
- **Payment process**: Stripe API
- **Language**: Kotlin
- **UI toolkit**: Jetpack Compose
- **Dependency Injection**: Dagger-Hilt
- **Map service**: Google Maps API
- **Identity verification**: VNPT eKYC
- **Chat service**: Stream Chat

![Alt text](https://github.com/PawSomeRMIT/PawsomePublic/assets/72547907/ce0bf8c1-84fe-404e-a604-a03659e6b97e "Optional title")

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

We are actively working to address these limitations and hope to provide updates that enhance the application's capabilities without compromising on ease of use and security.

## Screenshot
![Alt text](https://private-user-images.githubusercontent.com/72547907/297799083-76a6fe14-d393-4c68-bf84-250dd5bebe40.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTkwODMtNzZhNmZlMTQtZDM5My00YzY4LWJmODQtMjUwZGQ1YmViZTQwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTE5OWZhYjM2ZDFjN2EwOTRkMzViMzQ3NWUzZTA4MjAyNGMxYzE0MmY4NjA1NWJjY2NjOGY5ZjMzYjJhZDQ4NDImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.hFcCCAByPxXAjL1eQtGDt87f5Z-JhmQIN4P01SucKEk "Pawsome UI 1")
![Alt text](https://private-user-images.githubusercontent.com/72547907/297798948-93bf8132-3f65-4598-b170-28b950edc148.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTg5NDgtOTNiZjgxMzItM2Y2NS00NTk4LWIxNzAtMjhiOTUwZWRjMTQ4LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTBlYTQxNTk3MzFjN2MzNGMxOTJiNDYyN2ZkYjg3ODI0ZDQ1NjQ1NGU1M2MwZjhlZDU0OWJlNDJjM2JmMDZkMDImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.ipzhES3GbCsE-NOYI6856Qkl7JGbjkwCT7Slu7Uhdwg "Optional title")
![Alt text](https://private-user-images.githubusercontent.com/72547907/297799000-78e494e9-1439-4aad-8c5a-f157fab47edb.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTkwMDAtNzhlNDk0ZTktMTQzOS00YWFkLThjNWEtZjE1N2ZhYjQ3ZWRiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTFjYzUyMTRmZmUxNzljYWYzMWFlZDVlYjYwNDFjNDE3YWQ4ODhkODU3OWVmMmIwNDMwMTZlM2M2MDEzZDM3ZjgmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.t79KQgdWqDsy8gtBGB-UnMDtzWVKlDUgNfXFYnOdR9o "Optional title")
![Alt text](https://private-user-images.githubusercontent.com/72547907/297799067-8cc9bc82-e47b-4379-b598-1dec27a613d1.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTkwNjctOGNjOWJjODItZTQ3Yi00Mzc5LWI1OTgtMWRlYzI3YTYxM2QxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTYyYmIxYTljYTQwNmQ2ODNiN2U1OTA1ZGE2ZTY5YzI5ZjkwMjg1ZjZlNzBhMzgxODJlMzZhYTFhMTI1N2FlZjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.J1oFAzG6BYGZOoIMtxjT3IosPKtzwd8SR2N7gpI3z5U "Optional title")

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


