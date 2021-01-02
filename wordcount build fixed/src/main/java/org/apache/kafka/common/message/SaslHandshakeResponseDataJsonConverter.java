/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// THIS CODE IS AUTOMATICALLY GENERATED.  DO NOT EDIT.

package org.apache.kafka.common.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.ArrayList;
import org.apache.kafka.common.protocol.MessageUtil;

import static org.apache.kafka.common.message.SaslHandshakeResponseData.*;

public class SaslHandshakeResponseDataJsonConverter {
    public static SaslHandshakeResponseData read(JsonNode _node, short _version) {
        SaslHandshakeResponseData _object = new SaslHandshakeResponseData();
        JsonNode _errorCodeNode = _node.get("errorCode");
        if (_errorCodeNode == null) {
            throw new RuntimeException("SaslHandshakeResponseData: unable to locate field 'errorCode', which is mandatory in version " + _version);
        } else {
            _object.errorCode = MessageUtil.jsonNodeToShort(_errorCodeNode, "SaslHandshakeResponseData");
        }
        JsonNode _mechanismsNode = _node.get("mechanisms");
        if (_mechanismsNode == null) {
            throw new RuntimeException("SaslHandshakeResponseData: unable to locate field 'mechanisms', which is mandatory in version " + _version);
        } else {
            if (!_mechanismsNode.isArray()) {
                throw new RuntimeException("SaslHandshakeResponseData expected a JSON array, but got " + _node.getNodeType());
            }
            ArrayList<String> _collection = new ArrayList<String>();
            _object.mechanisms = _collection;
            for (JsonNode _element : _mechanismsNode) {
                if (!_element.isTextual()) {
                    throw new RuntimeException("SaslHandshakeResponseData element expected a string type, but got " + _node.getNodeType());
                }
                _collection.add(_element.asText());
            }
        }
        return _object;
    }
    public static JsonNode write(SaslHandshakeResponseData _object, short _version, boolean _serializeRecords) {
        ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
        _node.set("errorCode", new ShortNode(_object.errorCode));
        ArrayNode _mechanismsArray = new ArrayNode(JsonNodeFactory.instance);
        for (String _element : _object.mechanisms) {
            _mechanismsArray.add(new TextNode(_element));
        }
        _node.set("mechanisms", _mechanismsArray);
        return _node;
    }
    public static JsonNode write(SaslHandshakeResponseData _object, short _version) {
        return write(_object, _version, true);
    }
}
