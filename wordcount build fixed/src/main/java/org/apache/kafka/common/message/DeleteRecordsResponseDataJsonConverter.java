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
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.kafka.common.protocol.MessageUtil;

import static org.apache.kafka.common.message.DeleteRecordsResponseData.*;

public class DeleteRecordsResponseDataJsonConverter {
    public static DeleteRecordsResponseData read(JsonNode _node, short _version) {
        DeleteRecordsResponseData _object = new DeleteRecordsResponseData();
        JsonNode _throttleTimeMsNode = _node.get("throttleTimeMs");
        if (_throttleTimeMsNode == null) {
            throw new RuntimeException("DeleteRecordsResponseData: unable to locate field 'throttleTimeMs', which is mandatory in version " + _version);
        } else {
            _object.throttleTimeMs = MessageUtil.jsonNodeToInt(_throttleTimeMsNode, "DeleteRecordsResponseData");
        }
        JsonNode _topicsNode = _node.get("topics");
        if (_topicsNode == null) {
            throw new RuntimeException("DeleteRecordsResponseData: unable to locate field 'topics', which is mandatory in version " + _version);
        } else {
            if (!_topicsNode.isArray()) {
                throw new RuntimeException("DeleteRecordsResponseData expected a JSON array, but got " + _node.getNodeType());
            }
            DeleteRecordsTopicResultCollection _collection = new DeleteRecordsTopicResultCollection();
            _object.topics = _collection;
            for (JsonNode _element : _topicsNode) {
                _collection.add(DeleteRecordsTopicResultJsonConverter.read(_element, _version));
            }
        }
        return _object;
    }
    public static JsonNode write(DeleteRecordsResponseData _object, short _version, boolean _serializeRecords) {
        ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
        _node.set("throttleTimeMs", new IntNode(_object.throttleTimeMs));
        ArrayNode _topicsArray = new ArrayNode(JsonNodeFactory.instance);
        for (DeleteRecordsTopicResult _element : _object.topics) {
            _topicsArray.add(DeleteRecordsTopicResultJsonConverter.write(_element, _version, _serializeRecords));
        }
        _node.set("topics", _topicsArray);
        return _node;
    }
    public static JsonNode write(DeleteRecordsResponseData _object, short _version) {
        return write(_object, _version, true);
    }
    
    public static class DeleteRecordsPartitionResultJsonConverter {
        public static DeleteRecordsPartitionResult read(JsonNode _node, short _version) {
            DeleteRecordsPartitionResult _object = new DeleteRecordsPartitionResult();
            JsonNode _partitionIndexNode = _node.get("partitionIndex");
            if (_partitionIndexNode == null) {
                throw new RuntimeException("DeleteRecordsPartitionResult: unable to locate field 'partitionIndex', which is mandatory in version " + _version);
            } else {
                _object.partitionIndex = MessageUtil.jsonNodeToInt(_partitionIndexNode, "DeleteRecordsPartitionResult");
            }
            JsonNode _lowWatermarkNode = _node.get("lowWatermark");
            if (_lowWatermarkNode == null) {
                throw new RuntimeException("DeleteRecordsPartitionResult: unable to locate field 'lowWatermark', which is mandatory in version " + _version);
            } else {
                _object.lowWatermark = MessageUtil.jsonNodeToLong(_lowWatermarkNode, "DeleteRecordsPartitionResult");
            }
            JsonNode _errorCodeNode = _node.get("errorCode");
            if (_errorCodeNode == null) {
                throw new RuntimeException("DeleteRecordsPartitionResult: unable to locate field 'errorCode', which is mandatory in version " + _version);
            } else {
                _object.errorCode = MessageUtil.jsonNodeToShort(_errorCodeNode, "DeleteRecordsPartitionResult");
            }
            return _object;
        }
        public static JsonNode write(DeleteRecordsPartitionResult _object, short _version, boolean _serializeRecords) {
            ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
            _node.set("partitionIndex", new IntNode(_object.partitionIndex));
            _node.set("lowWatermark", new LongNode(_object.lowWatermark));
            _node.set("errorCode", new ShortNode(_object.errorCode));
            return _node;
        }
        public static JsonNode write(DeleteRecordsPartitionResult _object, short _version) {
            return write(_object, _version, true);
        }
    }
    
    public static class DeleteRecordsTopicResultJsonConverter {
        public static DeleteRecordsTopicResult read(JsonNode _node, short _version) {
            DeleteRecordsTopicResult _object = new DeleteRecordsTopicResult();
            JsonNode _nameNode = _node.get("name");
            if (_nameNode == null) {
                throw new RuntimeException("DeleteRecordsTopicResult: unable to locate field 'name', which is mandatory in version " + _version);
            } else {
                if (!_nameNode.isTextual()) {
                    throw new RuntimeException("DeleteRecordsTopicResult expected a string type, but got " + _node.getNodeType());
                }
                _object.name = _nameNode.asText();
            }
            JsonNode _partitionsNode = _node.get("partitions");
            if (_partitionsNode == null) {
                throw new RuntimeException("DeleteRecordsTopicResult: unable to locate field 'partitions', which is mandatory in version " + _version);
            } else {
                if (!_partitionsNode.isArray()) {
                    throw new RuntimeException("DeleteRecordsTopicResult expected a JSON array, but got " + _node.getNodeType());
                }
                DeleteRecordsPartitionResultCollection _collection = new DeleteRecordsPartitionResultCollection();
                _object.partitions = _collection;
                for (JsonNode _element : _partitionsNode) {
                    _collection.add(DeleteRecordsPartitionResultJsonConverter.read(_element, _version));
                }
            }
            return _object;
        }
        public static JsonNode write(DeleteRecordsTopicResult _object, short _version, boolean _serializeRecords) {
            ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
            _node.set("name", new TextNode(_object.name));
            ArrayNode _partitionsArray = new ArrayNode(JsonNodeFactory.instance);
            for (DeleteRecordsPartitionResult _element : _object.partitions) {
                _partitionsArray.add(DeleteRecordsPartitionResultJsonConverter.write(_element, _version, _serializeRecords));
            }
            _node.set("partitions", _partitionsArray);
            return _node;
        }
        public static JsonNode write(DeleteRecordsTopicResult _object, short _version) {
            return write(_object, _version, true);
        }
    }
}
