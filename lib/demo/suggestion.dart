import 'package:flutter/material.dart';
import 'package:flutter_app/global/global.dart';

class Suggestion extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final tiles = Global.saved.map((pair) => ListTile(
          title: Text(
            pair.asPascalCase,
            style: TextStyle(fontSize: 18),
          ),
        ));
    return Scaffold(
      appBar: AppBar(
        title: Text("Saved Suggestion"),
      ),
      body: ListView(
        children: ListTile.divideTiles(context: context, tiles: tiles).toList(),
      ),
    );
  }
}
