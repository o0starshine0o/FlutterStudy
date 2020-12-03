import 'package:flutter/material.dart';
import 'package:flutter_app/home/home.dart';
import 'package:flutter_app/words/suggestion.dart';

class RouterMap{
  static final _navigation = BottomNavigation(BottomNavigationDemoType.withoutLabels);
  static final _suggestion = Suggestion();
  static Map<String, Widget> get map => {'$BottomNavigation': _navigation, '$Suggestion': _suggestion};
}