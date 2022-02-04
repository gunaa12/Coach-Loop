import 'package:coaches_loop/components/buttons.dart';
import 'package:coaches_loop/constants.dart';
import 'package:coaches_loop/screens/editPage.dart';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class MainPage extends StatefulWidget {
  static const String id = 'main';

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  late final FirebaseAuth _auth;
  late final _user;

  @override
  void initState() {
    super.initState();

    _auth = FirebaseAuth.instance;
    _user = _auth.currentUser;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: kPlatinum,
      body: SafeArea(
        child: Container(
          child: Column(
            children: [
              AppBar(
                backgroundColor: kPlatinum,
                leading: Hero(
                  tag: 'logo',
                  child: Icon(
                    FontAwesomeIcons.penSquare,
                    size: 40,
                    color: Colors.black,
                  ),
                ),
                title: Text(
                  'Coaches\' Loop',
                  style: TextStyle(
                    color: kDefaultTextColor,
                    fontSize: 25,
                  ),
                ),

              ),
              Expanded(
                  child: Container(
                    child: getNotes(),
                  )
              ),
              Row(
                children: [
                  Container(
                    margin: EdgeInsets.all(15),
                    child: Button(
                        content: Text(
                          'Logout',
                          style: TextStyle(
                            color: kDefaultTextColor,
                          ),
                        ),
                        color: kYellow,
                        onPress: () {
                          _auth.signOut();
                          Navigator.pop(context);
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

  Widget getNotes() {
    return StreamBuilder<QuerySnapshot>(
        stream: kRef.snapshots(),
        builder: (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
          if (snapshot.hasData) {
            return ListView.builder(
              itemCount: snapshot?.data!.size ?? 0,
              itemBuilder: (context, _index) {
                var note = snapshot.data!.docs[_index];
                return Card(
                  margin: EdgeInsets.symmetric(vertical: 25, horizontal: 15),
                  color: kDarkGreen,
                  child: Hero(
                    tag: 'box',
                    child: MaterialButton(
                      onPressed: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => EditPage(name: note!['name'], school: note!['school'], review: note!['review'], wins: note!['wins'], losses: note!['losses'], avgPointWin: note!['avgPointWin'], overallRating: note!['overallRating'])));
                        FirebaseFirestore.instance.collection('Coaches').doc(note.id).delete();
                      },
                      child: Row(
                        children: [
                          Expanded(
                            flex: 3,
                            child: Column(
                                children: [
                                  SizedBox(height: 8,),
                                  Text(
                                    'Name: ${note!['name']}',
                                    style: kTitleText,
                                  ),
                                  SizedBox(height: 5,),
                                  Text(
                                    'School: ${note!['school']}',
                                  ),
                                  SizedBox(height: 10,),
                                ]
                            ),
                          ),
                          Expanded(
                            flex: 1,
                            child: Container(
                              decoration: BoxDecoration(border: Border(left: BorderSide(width: 2.0))),
                              padding: EdgeInsets.only(left: 25),
                              child: Text(
                                '${note!['overallRating']}',
                                style: TextStyle(
                                  color: kDefaultTextColor,
                                  fontSize: 30,
                                ),
                              ),
                            )
                          )
                        ],
                      ),
                    ),
                  ),
                );
              },
            );
          }
          else {
            return Center(
              child: Text('There are no notes to display!'),
            );
          }
        }
    );
  }
}