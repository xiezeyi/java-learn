-- 判断 hash的一个key  hexists标识它是否存在
-- key[1] redis的key  key2值hashmap的Key存不存在，现在指amount
-- 声明一个变量，通过内部函数获取 两个key，转成数字
-- 判断是否大于0  大于0则做一个自减的操作
if (redis.call('hexists', KEYS[1], KEYS[2]) == 1) then
	local stock = tonumber(redis.call('hget', KEYS[1], KEYS[2]));
	if (stock > 0) then
	   redis.call('hincrby', KEYS[1], KEYS[2], -1);
	   return stock;
	end;
    return 0;
end;