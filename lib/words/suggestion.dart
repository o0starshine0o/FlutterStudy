import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'saved_words.dart';

class Suggestion extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final tiles = Provider.of<SavedWords>(context, listen: false).words.map((pair) => ListTile(
            title: Text(
          pair.asPascalCase,
          style: TextStyle(fontSize: 18),
        )));
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
