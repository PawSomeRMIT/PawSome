For best experience, please view this file with a Markdown viewer

# COSC2657 - Android Development
![Logo Pawsome](https://private-user-images.githubusercontent.com/72547907/297799619-645b2ab4-6c9e-46d0-b12e-dfbe454b09b4.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU3MTEwOTYsIm5iZiI6MTcwNTcxMDc5NiwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTk2MTktNjQ1YjJhYjQtNmM5ZS00NmQwLWIxMmUtZGZiZTQ1NGIwOWI0LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMjAlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTIwVDAwMzMxNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWFlNGYzNWE2YjI4YmY1ZGU4NjE0YTJiNjhhNmNmNmU2MjYxYzcyNDM0Mjg1ZjE4ZTMzNTNiMjIyZGVkNjUwODQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.muLXNOwC9e3FP3eK2xoRygSt3kfIb-OJhP2OuAUDwsY "Pawsome Logo")
## Authors
- [Nhat Bui](https://github.com/nhat117) - s3878174
- [Thuc Thieu](https://github.com/ThucT4) - s3870730
- [Tri Lai](https://github.com/Tri-Lai) - s3799602
- [Ngan Phan](https://github.com/nganphan3903) - s3914582
## Introduction
Pawsome is an app that connects pet lovers with pet fosters who have dogs, cats, and other adorable animals that are waiting for their forever homes. Users can book a pet for an hour or a day, enjoy the companionship of a cuddly creature and learn how to raise and live with a pet before coming to an adopted decision. Or simply having fun with a pet and going to a coffee shop with friends.

Pawsome is easy to use and has two types of accounts: customer and foster. As a customer, users are able to browse a list of available pets for booking, see their photos, profiles, and reviews. They can chat with the foster directly and ask any questions related to pets. Moreover, they can access booking history and rate experience with the pet and the agency. The foster account requires users to do an upgrade and its process involves a transaction API and fee. As the foster, users can post new pets for booking and edit their information. You can also chat with the customers and arrange the details of the booking.


## Tech Stack

- **Firebase**: Authentication, Realtime Database, FireStore, Storage
- **Payment process**: Stripe API
- **Language**: Kotlin
- **UI toolkit**: Jetpack Compose
- **Dependency Injection**: Dagger-Hilt
- **Map service**: Google Maps API
- **Identity verification**: VNPT eKYC
- **Chat service**: Stream Chat

![Alt text](https://github-production-user-asset-6210df.s3.amazonaws.com/72547907/297805206-ce0bf8c1-84fe-404e-a604-a03659e6b97e.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA/20240120/us-east-1/s3/aws4_request&X-Amz-Date=20240120T003205Z&X-Amz-Expires=300&X-Amz-Signature=fb732f34bf42cfcf8618e4a7cdc225ed0c75b08245c1f19b203071e20a11b35b&X-Amz-SignedHeaders=host&actor_id=72547907&key_id=0&repo_id=745075134 "Optional title")

## Feature Overview

Our application offers a comprehensive suite of features designed to enhance user experience. Below is a breakdown of the main and extra features available in the app:

### Main Features (9/9 completed)

- **History**: Keep track of your past activities and transactions within the app.
- **Pet Detail**: Access in-depth information about pets, including health, history, and care instructions.
- **Recommendation**: Get personalized suggestions based on your preferences and previous interactions.
- **Booking**: Easily book appointments or services directly through the app.
- **Payment**: Secure and versatile payment options for all transactions within the app.
- **Settings (UI completion)**: Customize your app experience by adjusting the settings to your liking.
- **Notification**: User will received a notification if they have successfully create a pet for booking.
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

### No super user account:

- **No super user account**: Currently the application does not have a super user account role. This feature will be implemented in the future.

We are actively working to address these limitations and hope to provide updates that enhance the application's capabilities without compromising on ease of use and security.

## Application Flow Diagram
![Alt text](https://private-user-images.githubusercontent.com/72547907/297900584-9d2ae119-dd02-47a3-8d17-6908c27c5e58.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU2MTgwODQsIm5iZiI6MTcwNTYxNzc4NCwicGF0aCI6Ii83MjU0NzkwNy8yOTc5MDA1ODQtOWQyYWUxMTktZGQwMi00N2EzLThkMTctNjkwOGMyN2M1ZTU4LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDIyNDMwNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWI3OTE2YWQ1ZWJhYjFjNzNkOGMxY2U3NDlmNmYxZThmZjU1NDY3MGNlZWQyOTZkOTQ2OGMwNDRhODk3ODg2NzQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.hwdnSMH0JF2KYeMxhhJofb7SM7q5wZ9gcWigQ3Sxt5E "Pawsome UI 1")

## Screenshot
![Alt text](https://private-user-images.githubusercontent.com/72547907/297799000-78e494e9-1439-4aad-8c5a-f157fab47edb.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU3MTEwOTYsIm5iZiI6MTcwNTcxMDc5NiwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTkwMDAtNzhlNDk0ZTktMTQzOS00YWFkLThjNWEtZjE1N2ZhYjQ3ZWRiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMjAlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTIwVDAwMzMxNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWU2MjZlZTI5M2M0ZDdmNTUzYWUyMzY2ZDc1ZGQyNTIxMmQ4MTMzOWRiN2M0YTQ1NjA4MDdmOTQ0N2ViZmU5MjYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.8WZfhAwuGFFDB8aQj5FWDV6QUuVy-5RGkaaArgw6GRQ "Pawsome UI 1")
![Alt text](https://private-user-images.githubusercontent.com/72547907/297798948-93bf8132-3f65-4598-b170-28b950edc148.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTg5NDgtOTNiZjgxMzItM2Y2NS00NTk4LWIxNzAtMjhiOTUwZWRjMTQ4LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTBlYTQxNTk3MzFjN2MzNGMxOTJiNDYyN2ZkYjg3ODI0ZDQ1NjQ1NGU1M2MwZjhlZDU0OWJlNDJjM2JmMDZkMDImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.ipzhES3GbCsE-NOYI6856Qkl7JGbjkwCT7Slu7Uhdwg "Optional title")
![Alt text](https://private-user-images.githubusercontent.com/72547907/297799000-78e494e9-1439-4aad-8c5a-f157fab47edb.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTkwMDAtNzhlNDk0ZTktMTQzOS00YWFkLThjNWEtZjE1N2ZhYjQ3ZWRiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTFjYzUyMTRmZmUxNzljYWYzMWFlZDVlYjYwNDFjNDE3YWQ4ODhkODU3OWVmMmIwNDMwMTZlM2M2MDEzZDM3ZjgmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.t79KQgdWqDsy8gtBGB-UnMDtzWVKlDUgNfXFYnOdR9o "Optional title")
![Alt text](https://private-user-images.githubusercontent.com/72547907/297799067-8cc9bc82-e47b-4379-b598-1dec27a613d1.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDU1OTMwMjEsIm5iZiI6MTcwNTU5MjcyMSwicGF0aCI6Ii83MjU0NzkwNy8yOTc3OTkwNjctOGNjOWJjODItZTQ3Yi00Mzc5LWI1OTgtMWRlYzI3YTYxM2QxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAxMTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMTE4VDE1NDUyMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTYyYmIxYTljYTQwNmQ2ODNiN2U1OTA1ZGE2ZTY5YzI5ZjkwMjg1ZjZlNzBhMzgxODJlMzZhYTFhMTI1N2FlZjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.J1oFAzG6BYGZOoIMtxjT3IosPKtzwd8SR2N7gpI3z5U "Optional title")

## Application Demo Video
Application demo video can be found [here](https://rmiteduau-my.sharepoint.com/:v:/g/personal/s3870730_rmit_edu_vn/EYPXAPmDDPpNh0UhCRRsg-0BNxFU9Io--DruPORMlgpCFw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=njbIWD)

## Team Work Distribution
| Team Member  | Main Features                             | Extra Features         | Contribution |
|--------------|-------------------------------------------|------------------------|--------------|
| Nhat Bui     | History, Pet Detail (50%)                 | Authentication         | 25%          |
| Thuc Thieu   | Recommendation, Pet Detail (50%)          | ID card verification   | 25%          |
| Tri Lai      | Booking, Membership                       |                        | 25%          |
| Ngan Phan    | Payment, Setting (UI completion), Notification |                    | 25%          |


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


