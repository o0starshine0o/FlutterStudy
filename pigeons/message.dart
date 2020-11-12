import 'package:pigeon/pigeon.dart';

class SearchRequest{
  String query;
}

class SearchReplay{
  String result;
}

@HostApi()
abstract class Api{
  SearchReplay search(SearchRequest request);
}