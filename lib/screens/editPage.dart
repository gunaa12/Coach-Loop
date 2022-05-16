import 'package:coaches_loop/components/buttons.dart';
import 'package:coaches_loop/constants.dart';

import 'dart:async';
import 'package:flutter/services.dart';
import 'package:starflut/starflut.dart';

import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class EditPage extends StatefulWidget {
  static const String id = 'editPage';

  // not going to be edited
  String name;
  String school;
  String review;

  var wins;
  var losses;
  var avgPointWin;
  var overallRating;

  // going to be appended to existing
  late String newReview;

  EditPage({required this.name, required this.school, required this.review, required this.wins, required this.losses, required this.avgPointWin, required this.overallRating});

  @override
  _EditPageState createState() => _EditPageState();
}

class _EditPageState extends State<EditPage> {
  late final FirebaseAuth _auth;
  late final _user;

  late TextEditingController _name;
  late TextEditingController _content;

  @override
  void initState() {
    initPlatformState();
    print("hello, right here!!");
    super.initState();

    _auth = FirebaseAuth.instance;
    _user = _auth.currentUser;

    _content = TextEditingController();
    calculateOverallScore();
    print(widget.overallRating);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: kPlatinum,
      body: SafeArea(
        child: Column(
          children: [
            AppBar(
              backgroundColor: Colors.white,
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
                  margin: EdgeInsets.only(top: 25, left: 15, right: 15, bottom: 5),
                  child: Column(
                    children: [
                      Container(
                        decoration: BoxDecoration(border: Border.all()),
                        child: Hero(
                          tag: 'box',
                          child: MaterialButton(
                            onPressed: () {},
                            child: Row(
                              children: [
                                Expanded(
                                  flex: 3,
                                  child: Column(
                                      children: [
                                        SizedBox(height: 8,),
                                        Text(
                                          'Name: ${widget.name}',
                                          style: kTitleText,
                                        ),
                                        SizedBox(height: 5,),
                                        Text(
                                          'School: ${widget.school}',
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
                                        '${widget.overallRating}',
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
                      ),
                      SizedBox(height: 15.0,),
                      Expanded(
                        child: Container(
                          decoration: BoxDecoration(border: Border.all()),
                          child: TextField(
                            controller: _content,
                            maxLines: null,
                            decoration: InputDecoration(hintText: '  Review'),
                          ),
                        ),
                      ),
                    ],
                  ),
                )
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                  margin: EdgeInsets.all(15),
                  child: Button(
                      content: Icon(Icons.save),
                      width: 145,
                      color: Color(0xFF75B9BE),
                      onPress: () {
                        kRef.add({
                          'name': widget.name,
                          'review': widget.review + "; " +_content.text,
                          'school': widget.school,
                          'wins': widget.wins,
                          'losses': widget.losses,
                          'avgPointWin': widget.avgPointWin,
                          'overallRating': widget.overallRating,
                        });
                        Navigator.pop(context);
                      }
                  ),
                ),
                Container(
                  margin: EdgeInsets.all(15),
                  child: Button(
                      content: Icon(Icons.delete),
                      width: 145,
                      color: Color(0xFF75B9BE),
                      onPress: () {
                        kRef.add({
                          'name': widget.name,
                          'review': widget.review,
                          'school': widget.school,
                          'wins': widget.wins,
                          'losses': widget.losses,
                          'avgPointWin': widget.avgPointWin,
                          'overallRating': widget.overallRating,
                        });
                        Navigator.pop(context);
                      }
                  ),
                ),
              ],
            )
          ],
        ),
      ),
    );
  }

  void calculateOverallScore() {
    var winLossMargin = widget.wins.toDouble() / widget.losses;
    if (winLossMargin <= .1) winLossMargin = 0;
    else if (winLossMargin > 1) winLossMargin = 1;
    var pointWeight;
    if (widget.avgPointWin < 3) pointWeight = .2;
    else if (widget.avgPointWin < 6) pointWeight = .4;
    else if (widget.avgPointWin < 10) pointWeight = .6;
    else if (widget.avgPointWin < 14) pointWeight = .8;
    else pointWeight = 1;
    widget.overallRating = ((winLossMargin + pointWeight) * 2.5);
  }

  @override
  void dispose() {
    super.dispose();
    _name.dispose();
    _content.dispose();
  }

  // Future<void> initPlatformState() async {
  //   String platformVersion;
  //
  //   try {
  //     String Path1 = await Starflut.getResourcePath();
  //     String Path2 = await Starflut.getAssetsPath();
  //     StarCoreFactory starcore = await Starflut.getFactory();
  //     StarServiceClass Service = await starcore.initSimple(
  //         "test", "123", 0, 0, []);
  //     await starcore.regMsgCallBackP(
  //             (int serviceGroupID, int uMsg, Object wParam,
  //             Object lParam) async {
  //           print("$serviceGroupID  $uMsg   $wParam   $lParam");
  //           return null;
  //         });
  //     var SrvGroup = await Service["_ServiceGroup"];
  //
  //     /*---script python--*/
  //     bool isAndroid = await Starflut.isAndroid();
  //     if (isAndroid == true) {
  //       await Starflut.copyFileFromAssets(
  //           "testcallback.py", "flutter_assets/starfiles",
  //           "flutter_assets/starfiles");
  //       await Starflut.copyFileFromAssets(
  //           "testpy.py", "flutter_assets/starfiles",
  //           "flutter_assets/starfiles");
  //       await Starflut.copyFileFromAssets(
  //           "python3.7.zip", null, null); //desRelatePath must be null
  //
  //       var nativepath = await Starflut.getNativeLibraryDir();
  //       var LibraryPath = "";
  //       if (nativepath.contains("x86_64"))
  //         LibraryPath = "x86_64";
  //       else if (nativepath.contains("arm64"))
  //         LibraryPath = "arm64-v8a";
  //       else if (nativepath.contains("arm"))
  //         LibraryPath = "armeabi";
  //       else if (nativepath.contains("x86"))
  //         LibraryPath = "x86";
  //
  //       await Starflut.copyFileFromAssets(
  //           "zlib.cpython-37.so", LibraryPath, null);
  //       await Starflut.copyFileFromAssets(
  //           "unicodedata.cpython-37.so", LibraryPath, null);
  //       await Starflut.loadLibrary("libpython3.7.so");
  //     }
  //
  //     String docPath = await Starflut.getDocumentPath();
  //     print("docPath = $docPath");
  //
  //     String resPath = await Starflut.getResourcePath();
  //     print("resPath = $resPath");
  //
  //     String assetsPath = await Starflut.getAssetsPath();
  //     print("assetsPath = $assetsPath");
  //
  //     dynamic rr1 = await SrvGroup.initRaw("python37", Service);
  //
  //     print("initRaw = $rr1");
  //     var Result = await SrvGroup.loadRawModule(
  //         "python", "", assetsPath + "/flutter_assets/starfiles/" + "testpy.py",
  //         false);
  //     print("loadRawModule = $Result");
  //
  //     dynamic python = await Service.importRawContext(
  //         null, "python", "", false, "");
  //     print("python = " + await python.getString());
  //
  //     StarObjectClass retobj = await python.call("tt", ["hello ", "world"]);
  //     print(await retobj[0]);
  //     print(await retobj[1]);
  //
  //     print(await python["g1"]);
  //
  //     StarObjectClass yy = await python.call("yy", ["hello ", "world", 123]);
  //     print(await yy.call("__len__", []));
  //
  //     StarObjectClass multiply = await Service.importRawContext(
  //         null, "python", "Multiply", true, "");
  //     StarObjectClass multiply_inst = await multiply.newObject(
  //         ["", "", 33, 44]);
  //     print(await multiply_inst.getString());
  //
  //     print(await multiply_inst.call("multiply", [11, 22]));
  //
  //     await SrvGroup.clearService();
  //     await starcore.moduleExit();
  //   } catch (e) {
  //     print(e);
  //   }
  // }
}