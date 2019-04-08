package org.lhyf.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.lhyf.entity.RequestEntity;

/****
 * @author YF
 * @date 2019-04-08 14:11
 * @desc ProtostuffSerializer
 *
 **/
public class ProtostuffSerializer {
    private static  Schema<RequestEntity> schema = RuntimeSchema.createFrom(RequestEntity.class);

    public static byte[] serialize(final RequestEntity entity) {
        final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            return serializeInternal(entity, schema, buffer);
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static RequestEntity deserialize(final byte[] bytes) {
        try {
            RequestEntity entity = deserializeInternal(bytes, schema.newMessage(), schema);
            if (entity != null) {
                return entity;
            }
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

    private static <T> byte[] serializeInternal(final T source, final Schema<T>
            schema, final LinkedBuffer buffer) {
        return ProtostuffIOUtil.toByteArray(source, schema, buffer);
    }

    private static <T> T deserializeInternal(final byte[] bytes, final T result, final
    Schema<T> schema) {
        ProtostuffIOUtil.mergeFrom(bytes, result, schema);
        return result;
    }
}
