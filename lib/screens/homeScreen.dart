import 'package:coaches_loop/components/buttons.dart';
import 'package:coaches_loop/constants.dart';
import 'package:coaches_loop/screens/loginScreen.dart';
import 'package:coaches_loop/screens/registrationScreen.dart';

import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';

class HomeScreen extends StatefulWidget {
  static const String id = 'home_screen';

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation _colorAnimation;

  @override
  void initState() {
    instantiateFirebase();
    _controller = AnimationController(
      vsync: this,
      duration: Duration(seconds: 1, milliseconds: 500),
    );
    _colorAnimation = ColorTween(begin: Colors.black, end: Colors.white).animate(CurvedAnimation(parent: _controller, curve: Curves.decelerate));
    _controller.forward();
    _controller.addListener(() {
      setState(() {

      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: _colorAnimation.value,
      body: SafeArea(
        child: Container(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Column(
                    children: [
                      Hero(tag: 'logo', child: kLogo),
                      SizedBox(width: 20),
                      Text(
                        'Coaches\' Loop',
                        style: TextStyle(
                          fontSize: 45.0,
                          fontWeight: FontWeight.w900,
                          color: kDefaultTextColor,
                        ),
                      ),
                    ],
                  )
                ],
              ),
              Column(
                children: [
                  Hero(
                    tag: 'login',
                    child: Button(
                        content: Text(
                          'Login',
                          style: TextStyle(
                            color: kDefaultTextColor,
                          ),
                        ),
                        color: kLightGreen,
                        onPress: () {
                          Navigator.pushNamed(context, LoginScreen.id);
                        }
                    ),
                  ),
                  Hero(
                    tag: 'register',
                    child: Button(
                        content: Text(
                          'Register',
                          style: TextStyle(
                            color: kDefaultTextColor,
                          ),
                        ),
                        color: kDarkGreen,
                        onPress: () {
                          Navigator.pushNamed(context, RegistrationScreen.id);
                        }
                    ),
                  ),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  void instantiateFirebase() async {
    await Firebase.initializeApp();
    print("got here");
  }
}