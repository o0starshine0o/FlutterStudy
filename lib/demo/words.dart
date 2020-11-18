import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_app/pigeons/messages.dart';

class RandomWords extends StatefulWidget {
  @override
  _RandomWordsState createState() => _RandomWordsState();
}

class _RandomWordsState extends State<RandomWords> {
  final _suggestion = <WordPair>[];
  final _saved = <WordPair>{};
  final _biggerFont = TextStyle(fontSize: 18);
  final _batteryChannel = MethodChannel('com.example.flutter_app/battery');
  int _batteryLevel = 0;
  String _replay = '';

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: TextButton(
            style: TextButton.styleFrom(primary: Colors.white),
            child: Text('$_replay: $_batteryLevel'),
            onPressed: _getBattery,
          ),
          actions: [IconButton(icon: Icon(Icons.list), onPressed: _pushSaved)],
        ),
        body: _buildSuggestions(),
      );

  Widget _buildSuggestions() => ListView.builder(
        padding: EdgeInsets.all(16),
        itemBuilder: (context, i) {
          if (i >= _suggestion.length) _suggestion.addAll(generateWordPairs().take(10));
          return _buildRow(_suggestion[i]);
        },
      );

  Widget _buildRow(WordPair pair) {
    var alreadySaved = _saved.contains(pair);
    return Column(
      children: [
        ListTile(
          title: Text(
            pair.asPascalCase,
            style: _biggerFont,
          ),
          trailing: Icon(
            alreadySaved ? Icons.favorite : Icons.favorite_border,
            color: alreadySaved ? Colors.red : null,
          ),
          onTap: () => _triggerSaved(pair, alreadySaved),
        ),
        Divider(),
      ],
    );
  }

  void _pushSaved() {
    Navigator.of(context).push(MaterialPageRoute(builder: (context) {
      final tiles = _saved.map((pair) => ListTile(
            title: Text(
              pair.asPascalCase,
              style: _biggerFont,
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
    }));
  }

  void _triggerSaved(WordPair pair, bool alreadySaved) => setState(() {
        alreadySaved ? _saved.remove(pair) : _saved.add(pair);
      });

  Future<void> _getBattery() async {
    try {
      int result = await _batteryChannel.invokeMethod('getBattery');
      SearchReplay replay = await Api().search(SearchRequest()..query = 'query');
      setState(() {
        _batteryLevel = result;
        _replay = replay.result;
      });
    } on PlatformException {
      setState(() {
        _batteryLevel = -1;
        _replay = '';
      });
    }
  }
}
