package com.bamboo.firstdemo.constant;
/**
 * 缓存命名规范：
 * 
 * 1. 业务实体类：小写复数
 *    - users, products, orders, categories
 *    
 * 2. 组合数据：使用下划线连接
 *    - user_profiles, product_details, order_items
 *    
 * 3. 功能缓存：描述功能
 *    - shopping_carts, user_sessions, page_cache
 *    
 * 4. 带版本：使用冒号分隔
 *    - users:v2, api:cache:v1
 *    
 * 5. 禁止：空字符串、单个字符、无意义名称
 *    - ❌ "", "cache", "data", "a", "temp"
 */
public interface CacheNamingConvention {
    // 常量定义
    String CACHE_USERS = "users";
    String CACHE_PRODUCTS = "products";
    String CACHE_ORDERS = "orders";
}