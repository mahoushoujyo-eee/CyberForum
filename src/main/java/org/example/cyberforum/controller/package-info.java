/**
 * 1. 使用ServiceResponse<>类
 * 2. Controller层只需要直接调用Service层，不需要额外的代码 => 使用@LogArgumentsAndResponse
 * 3. Post请求的所有参数都在RequestBody里，不要写在请求路径里
 * 4. 不要用RequestMapping
 * 5. 大括号风格要统一
 * 6. Mapper.xml文件里每个操作都要有空行
 */
package org.example.cyberforum.controller;