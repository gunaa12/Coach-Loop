import 'package:coaches_loop/screens/homeScreen.dart';
import 'package:coaches_loop/screens/loginScreen.dart';
import 'package:coaches_loop/screens/mainPage.dart';
import 'package:coaches_loop/screens/registrationScreen.dart';

import 'package:flutter/material.dart';

void main() => runApp(Notes());

class Notes extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: HomeScreen.id,
      routes: {
        HomeScreen.id: (context) => HomeScreen(),
        LoginScreen.id: (context) => LoginScreen(),
        RegistrationScreen.id: (context) => RegistrationScreen(),
        MainPage.id: (context) => MainPage(),
      },
    );
  }
}