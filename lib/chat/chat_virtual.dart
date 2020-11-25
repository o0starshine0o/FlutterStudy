import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ChatLayoutVirtual extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    final String viewType = 'ChatLayout';
    // Pass parameters to the platform side.
    final Map<String, dynamic> creationParams = <String, dynamic>{};
    return Expanded(
        child: AndroidView(
      viewType: viewType,
      layoutDirection: TextDirection.ltr,
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
    ));
  }
}
