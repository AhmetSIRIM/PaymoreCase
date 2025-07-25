# 🏪 Paymore Case Study

[![Demo Video](https://img.shields.io/badge/🎬_Demo_Video-Watch_Now-red?style=for-the-badge&logo=youtube)](https://drive.google.com/file/d/1lEG7acRnIb4sI8Hs6Wfo6ssDOmO_hFvT/view?usp=sharing)

> **Android Kotlin Developer (TPOS Devices) Technical Assessment Project**. Modern **TPOS demo payment application** with **NFC card payments**, **QR code scanning**, **MQTT integration**, and **comprehensive sales management**.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?style=flat-square&logo=material-design&logoColor=white)

---

## 🎯 **Case Requirements & Features**

This project demonstrates native Android development skills for **TPOS devices**, including QR/NFC integrations, and direct hardware resource utilization.

### **🏠 1. Main Menu Screen - Payment Method Selection**
- ✅ **Credit Card (NFC)** - EMV card payments with price input
- ✅ **Loyalty Card (NFC)** - Customer loyalty program support
- ✅ **QR Code Payment** - Camera-based scanning with ML Kit
- ✅ **Button-based navigation** - Each selection launches corresponding flow

### **📱 2. QR Code Payment Flow**
- ✅ **Camera activation** with ML Kit barcode scanning
- ✅ **30-second countdown timer** with automatic timeout
- ✅ **Manual exit** via 'Back' button
- ✅ **Audio notifications** - TextToSpeech + beep sound on success
- ✅ **Data parsing** - Product name, price, ID extraction from QR
- ✅ **JSON & plain text support** - Flexible QR format handling

### **💳 3. NFC Card Payments (Credit & Loyalty)**
- ✅ **NFC reader activation** in reader mode
- ✅ **Automatic transaction start** on card proximity detection
- ✅ **EMV data extraction** - Card UID, ATR, identity information
- ✅ **Real-time price input** with validation
- ✅ **Audio confirmation** - "Card accepted" announcements

### **📊 4. Sales Record Management**
- ✅ **Comprehensive data storage** - Payment method, timestamp, price, card UID, product ID
- ✅ **Room Database** - Local SQLite persistence
- ✅ **Sales listing screen** - View and delete records
- ✅ **Statistics dashboard** - Total sales count and amount
- ✅ **Real-time updates** - Flow-based reactive UI

### **📡 5. MQTT Integration** *(Architecture Ready)*
- 🔄 **JSON message format** - Case specification compliant
```json
{
  "deviceId": "TPOS1234",
  "paymentType": "QR", 
  "productId": "XYZ001",
  "price": 25.5,
  "timestamp": 1721958123
}
```
- 🔄 **Connection status indicator** - Visual MQTT status
- 🔄 **Publish confirmation** - User notification after success

### **🔊 6. Audio Notifications & Beep**
- ✅ **TextToSpeech integration** - Turkish language support
- ✅ **Case-compliant audio messages**:
  - "İşlem başlatıldı" - Transaction started
  - "Ürün başarıyla okundu" - QR scan success  
  - "Kart kabul edildi" - NFC card accepted
  - "Satın alma tamamlandı" - Transaction complete
  - "İşlem iptal edildi" - Timeout cancellation
- ✅ **Beep sound integration** - Payment completion audio

---

## 🏗️ **Architecture**

Built with **Clean Architecture** principles ensuring maintainable and testable code:

```
📁 Project Structure
├── 🎯 feature/
│   ├── main/           # Main menu & navigation
│   ├── nfcpayment/     # NFC payment flow
│   ├── qrpayment/      # QR code payment flow  
│   └── sales/          # Sales records management
├── 🧠 core/
│   ├── domain/         # Business logic & entities
│   ├── data/           # Repository implementations
│   ├── ui/             # Shared UI components
│   ├── common/         # Utilities & extensions
│   └── navigation/     # Navigation setup
```

### **🔄 Data Flow**
```
UI Layer (Compose) → ViewModel (StateFlow) → Repository → Data Source (Room/NFC/Camera)
```

### **🧩 Key Technical Components**
- **🎨 UI Layer**: Jetpack Compose with Material 3
- **🧠 Domain Layer**: Use cases and business entities
- **💾 Data Layer**: Room database + NFC/Camera integrations  
- **🔗 DI**: Hilt for dependency injection
- **🧭 Navigation**: Type-safe Compose Navigation

---

## 🚀 **Tech Stack Compliance**

| **Case Requirement** | **Implementation** | **Status** |
|---------------------|-------------------|------------|
| **Kotlin Development** | 100% Kotlin codebase | ✅ |
| **Clean Architecture** | UI/Domain/Data layers | ✅ |
| **ViewModel + StateFlow** | Modern reactive architecture | ✅ |
| **Room Database** | Local data persistence | ✅ |
| **Dagger-Hilt** | Dependency injection | ✅ |
| **MQTT Client** | Architecture ready | 🔄 |
| **NFC API** | EMV card reading | ✅ |
| **QR Scanning** | QR Code Scanner | ✅ |
| **TextToSpeech + Beep** | Audio feedback system | ✅ |
| **Jetpack Compose** | Modern UI toolkit | ✅ |

---

## 🛠️ **Installation & Setup**

### **📋 Prerequisites**
- **Android Studio** Hedgehog 2023.1.1+
- **JDK 17** or higher
- **Android SDK 34**
- **NFC-enabled Android device** for testing

### **🚀 Quick Start**

1. **Clone the repository**
```bash
git clone https://github.com/AhmetSIRIM/PaymoreCase.git
cd PaymoreCase
```

2. **Open in Android Studio**
   - Import project
   - Sync Gradle files
   - Wait for indexing to complete

3. **Build and run**
```bash
./gradlew assembleDebug
```

### **📱 Hardware Requirements**
- **NFC support** (for card payments)
- **Camera** (for QR scanning)
- **Audio output** (for TextToSpeech)
- **Android 7.0+ (API 24+)**

---

## 📱 **Usage & Testing**

### **🏠 Main Menu Navigation**
Start from the main menu with three payment options:
- **💳 Credit Card (NFC)**: EMV card payments with price input
- **🎯 Loyalty Card (NFC)**: Customer loyalty programs
- **📱 QR Code**: Camera-based QR scanning

### **💳 NFC Payment Flow**
1. **Enter amount** → Input price and confirm
2. **Approach card** → Hold NFC card near device
3. **Processing** → Card data extraction & validation
4. **Completion** → Audio confirmation + success dialog

### **📱 QR Payment Flow**
1. **Open scanner** → Camera activates with 30s timer
2. **Scan QR code** → Point camera at QR code
3. **Data parsing** → Extract product/price information
4. **Completion** → Audio confirmation + success dialog

### **📊 Sales Records Management**
- **View all transactions** with payment details
- **Statistics overview** (total sales, amounts)
- **Delete records** individually
- **Payment type filtering**

---

## 🧪 **Testing Guide**

### **📱 NFC Testing**
- Use any **EMV contactless card** (Visa, Mastercard)
- **Approach card** to device back panel
- **Price input** required before NFC activation

### **📱 QR Testing**
Create test QR codes with these formats:

**JSON Format (Recommended):**
```json
{"productId":"PRD001","productName":"Test Coffee","price":"25.50"}
```

**Simple Text:**
```
Test Product - 15.00 TL
```

### **🔧 Debug Features**
- **Comprehensive logging** for all payment flows
- **Processing state visualization**
- **Audio feedback testing**
- **Database inspection** via Android Studio

---

## 📂 **Project Structure**

<details>
<summary>📁 <strong>Detailed File Structure</strong></summary>

```
PaymoreCase/
├── 📱 app/
│   ├── src/main/
│   │   ├── java/com/ahmetsirim/paymorecase/
│   │   │   ├── ui/MainActivity.kt
│   │   │   └── PaymoreCaseApplication.kt
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── 🧠 core/
│   ├── domain/
│   │   ├── model/
│   │   │   ├── SalesRecord.kt
│   │   │   └── common/PaymentTypeEnum.kt
│   │   ├── repository/SalesRepository.kt
│   │   └── service/
│   │       ├── AudioPlayer.kt
│   │       └── VibrationService.kt
│   ├── data/
│   │   ├── local/
│   │   │   ├── entity/SalesRecordEntity.kt
│   │   │   ├── dao/SalesRecordDao.kt
│   │   │   ├── database/PaymoreCaseDatabase.kt
│   │   │   └── converter/Converters.kt
│   │   ├── repository/SalesRepositoryImpl.kt
│   │   ├── service/AndroidAudioPlayer.kt
│   │   └── di/
│   │       ├── DatabaseModule.kt
│   │       └── ServiceModule.kt
│   ├── ui/
│   │   ├── component/
│   │   │   ├── InformationalDialog.kt
│   │   │   └── PaymentTopBar.kt
│   │   └── theme/PaymoreCaseTheme.kt
│   ├── common/
│   └── navigation/PaymoreCaseNavHost.kt
├── 🎯 feature/
│   ├── main/
│   │   ├── MainRoute.kt
│   │   ├── MainScreen.kt
│   │   ├── MainContainer.kt
│   │   └── MainViewModel.kt
│   ├── nfcpayment/
│   │   ├── NfcPaymentRoute.kt
│   │   ├── NfcPaymentScreen.kt
│   │   ├── NfcPaymentContainer.kt
│   │   └── NfcPaymentViewModel.kt
│   ├── qrpayment/
│   │   ├── QrPaymentRoute.kt
│   │   ├── QrPaymentScreen.kt
│   │   ├── QrPaymentContainer.kt
│   │   └── QrPaymentViewModel.kt
│   └── sales/
│       ├── SalesRoute.kt
│       ├── SalesScreen.kt
│       ├── SalesContainer.kt
│       └── SalesViewModel.kt
└── gradle/
    └── libs.versions.toml
```
</details>

---

## 📋 **Case Completion Checklist**

### **✅ Core Requirements**
- [x] **Kotlin Development** - 100% compliance
- [x] **Clean Architecture** - UI/Domain/Data separation
- [x] **ViewModel + StateFlow** - Reactive state management
- [x] **Room Database** - Local data persistence
- [x] **Dagger-Hilt** - Dependency injection
- [x] **NFC API Integration** - EMV card reading
- [x] **QR Code Scanning** - ML Kit implementation
- [x] **TextToSpeech + Beep** - Audio feedback
- [x] **Jetpack Compose** - Modern UI framework

### **✅ Feature Implementation**
- [x] **Main Menu** - 3 payment options
- [x] **QR Payment** - 30s timer, audio feedback, data parsing
- [x] **NFC Payment** - EMV reading, price input, card data
- [x] **Sales Management** - CRUD operations, statistics
- [x] **Audio System** - Case-compliant announcements
- [x]  **TR/EN Localization** - Currently Turkish
- [ ]  
### **🔄 Ready for Extension**
- [ ] **MQTT Integration** - Architecture prepared
- [ ] **Unit Testing** - Framework ready

---

## 🎯 **Technical Highlights**

### **🔧 Modern Android Development**
- **100% Jetpack Compose** - No XML layouts
- **Type-safe Navigation** - Serializable routes
- **Flow-based reactive UI** - Real-time updates
- **Hilt dependency injection** - Modular architecture

### **💳 NFC Implementation**
- **EMV NFC Card Library** integration
- **Reader Mode** for optimal detection
- **Comprehensive data extraction** - Card number, expiry, UID
- **Error handling** with user feedback

### **📱 QR Implementation**
- **ML Kit Barcode Scanning** - Google's ML solution
- **Camera lifecycle management** - Proper resource handling
- **JSON parsing** - Flexible data format support
- **Timeout handling** - 30-second auto-close

---

## 🙏 **Acknowledgments**

- **EMV NFC Card Library** by [devnied](https://github.com/devnied/EMV-NFC-Paycard-Enrollment)
- **QR Code Scanner** by Yuriy Budiyev for QR code scanning
- **Jetpack Compose** team for the modern UI toolkit

---

<div align="center">

**[Watch Demo Video](https://drive.google.com/file/d/1lEG7acRnIb4sI8Hs6Wfo6ssDOmO_hFvT/view?usp=sharing)**

</div>