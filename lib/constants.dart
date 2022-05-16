import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

// Link to the color palette for this project:
// https://coolors.co/221d23-8ba6a9-d1603d-ddb967-75b9be

const kDefaultTextColor = Color(0xFF12130F);
const kYellow = Color(0xFFFFCF70);
const kDarkGreen = Color(0xFF5B9279);
const kLightGreen = Color(0xFF8FCB9B);
const kPlatinum = Color(0xFFEAE6E5);

const double kDefaultButtonWidth = 350.0;

final CollectionReference kRef = FirebaseFirestore.instance.collection('Coaches');

const kTitleText = TextStyle(
  color: kDefaultTextColor,
  fontSize: 22,
);

const kLogo = Icon(
  FontAwesomeIcons.penSquare,
  size: 250,
);

const kInputFieldDecoration = InputDecoration(
  hintText: 'Enter a value',
  contentPadding:
  EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
  border: OutlineInputBorder(
    borderRadius: BorderRadius.all(Radius.circular(32.0)),
  ),
  enabledBorder: OutlineInputBorder(
    borderSide: BorderSide(color: Colors.blueAccent, width: 1.0),
    borderRadius: BorderRadius.all(Radius.circular(32.0)),
  ),
  focusedBorder: OutlineInputBorder(
    borderSide: BorderSide(color: Colors.blueAccent, width: 2.0),
    borderRadius: BorderRadius.all(Radius.circular(32.0)),
  ),
);