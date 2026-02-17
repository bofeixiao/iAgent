# Developer Quick Reference - API Compatibility Guide

## DO's & DON'Ts Quick Guide

### ✅ PageResult Usage (CORRECT)

```java
// Using factory method build()
return PageResult.build(userVOList, userPage.getTotal(), userPage.getCurrent(), userPage.getSize());

// Using empty factory for placeholder results
return PageResult.empty(pageNum.longValue(), pageSize.longValue());
```

### ❌ PageResult Usage (INCORRECT - DON'T USE)

```java
// DON'T: Method doesn't exist
return PageResult.of(userVOList, userPage.getTotal());

// DON'T: Missing current and size parameters
return PageResult.build(userVOList, userPage.getTotal());
```

---

### ✅ RedisService Cache Operations (CORRECT)

```java
// Modern approach: Using set() with TimeUnit
redisService.set(cacheKey, userVO, USER_CACHE_TIMEOUT, TimeUnit.HOURS);

// Legacy approach: Using setEx() with seconds
redisService.setEx(cacheKey, userVO, 3600); // 3600 seconds = 1 hour

// Retrieving from cache with type safety
UserVO userVO = redisService.get(cacheKey, UserVO.class);

// Deleting from cache
redisService.delete(cacheKey);
```

### ❌ RedisService Cache Operations (INCORRECT - DON'T USE)

```java
// DON'T: Wrong parameter order or count
redisService.setEx(cacheKey, userVO, USER_CACHE_TIMEOUT, TimeUnit.HOURS);

// DON'T: Forgetting to cast generic type
Object obj = redisService.get(cacheKey);  // Unsafe, loses type info

// DON'T: Not handling null properly
UserVO vo = redisService.get(cacheKey, UserVO.class);  // Could be null
```

---

## Complete Pattern Examples

### User Service Cache Pattern (Best Practice)

```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final RedisService redisService;
    private static final String USER_CACHE_PREFIX = "user:info:";
    private static final long USER_CACHE_TIMEOUT = 1; // hours
    
    @Override
    public UserVO getUserById(Long id) {
        String cacheKey = USER_CACHE_PREFIX + id;
        
        // 1. Try cache first
        UserVO userVO = redisService.get(cacheKey, UserVO.class);
        if (userVO != null) {
            return userVO;
        }
        
        // 2. Load from database
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        
        // 3. Convert to VO
        userVO = convertToVO(user);
        
        // 4. Cache the result with TTL (CORRECT: set with TimeUnit)
        redisService.set(cacheKey, userVO, USER_CACHE_TIMEOUT, TimeUnit.HOURS);
        
        return userVO;
    }
}
```

### Pagination Service Pattern (Best Practice)

```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    
    @Override
    public PageResult<UserVO> getUserList(UserQueryDTO queryDTO) {
        // 1. Build query criteria
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(queryDTO.getUsername()), 
                     User::getUsername, queryDTO.getUsername())
               .orderByDesc(User::getCreateTime);
        
        // 2. Execute paginated query
        Page<User> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<User> userPage = userMapper.selectPage(page, wrapper);
        
        // 3. Convert records to VO
        List<UserVO> userVOList = userPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        // 4. Return paginated result using factory method (CORRECT)
        return PageResult.build(
            userVOList, 
            userPage.getTotal(), 
            userPage.getCurrent(), 
            userPage.getSize()
        );
    }
    
    private UserVO convertToVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
```

### Controller with Pagination (Best Practice)

```java
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;
    
    @GetMapping("/list")
    public Result<PageResult<ArticleVO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category) {
        
        // TODO: Implement actual query logic
        
        // For placeholder/empty results
        return Result.success(PageResult.empty(pageNum.longValue(), pageSize.longValue()));
    }
}
```

---

## Testing Your Changes

### Before Committing

1. **Compile Check**
   ```bash
   mvn clean compile -f backend/pom.xml
   ```

2. **Run Tests**
   ```bash
   mvn test -f backend/pom.xml
   ```

3. **Full Build**
   ```bash
   mvn clean package -f backend/pom.xml
   ```

### IDE Tips

- **IntelliJ IDEA**: Use `Ctrl+B` to jump to method definition and verify signature
- **VS Code**: Use Go to Definition to verify API contracts
- **All IDEs**: Enable compilation warnings to catch API mismatches early

---

## Common Mistakes & Solutions

| Mistake | Solution | Reference |
|---------|----------|-----------|
| Using `PageResult.of()` | Use `PageResult.build()` or `PageResult.empty()` | [PageResult.java](../idata-common/src/main/java/com/idataai/common/core/domain/PageResult.java) |
| `setEx()` with 4 params | Use `set()` with `TimeUnit.HOURS` instead | [RedisService.java](../idata-common/src/main/java/com/idataai/common/redis/service/RedisService.java) |
| Unsafe `redisService.get()` | Use `get(key, Class.class)` for type safety | [RedisService.java](../idata-common/src/main/java/com/idataai/common/redis/service/RedisService.java) |
| Null pointer from cache | Always check if result is null before using | [UserServiceImpl.java](../idata-system/src/main/java/com/idataai/system/service/impl/UserServiceImpl.java) |

---

## When to Escalate

Contact the tech lead if you encounter:
- ❌ Method doesn't exist errors that don't match this guide
- ❌ Unexpected signature mismatches after library updates
- ❌ Breaking changes in dependencies
- ❌ Type casting issues with generic methods
- ❌ New deprecation warnings in compilation

---

**Last Updated:** 2026-02-17  
**For issues:** Check CI_BUILD_CHECKLIST.md
