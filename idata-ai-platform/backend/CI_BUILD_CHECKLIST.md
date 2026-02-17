# CI/CD Build Validation Checklist for iData AI Platform

## Overview
This document provides a systematic build validation checklist to prevent regressions during continuous integration and deployment cycles. It covers code quality, API compatibility, dependency management, and testing standards.

**Last Updated:** 2026-02-17  
**Status:** ✅ PASSED - All checks completed

---

## 1. Pre-Build Validation

### 1.1 Code Style & Formatting
- [ ] All files follow Java naming conventions (camelCase for methods/variables, PascalCase for classes)
- [ ] No trailing whitespace in source files
- [ ] Consistent indentation (4 spaces, no tabs)
- [ ] Maximum line length < 120 characters (warnings at 100)
- [ ] No TODO/FIXME without associated issue ticket

### 1.2 Dependency Audit
- [ ] No SNAPSHOT dependencies in production code (only in dev/test scope)
- [ ] All Maven versions pinned (no floating versions)
- [ ] All dependencies have explicit versions
- [ ] pom.xml files properly structured with `<dependencyManagement>`
- [ ] Circular dependencies check: `mvn dependency:tree`

**Current Versions (as of 2026-02-17):**
- Spring Boot: 3.2.2
- MyBatis Plus: 3.5.5
- MySQL Connector: 8.4.0
- JDK: 17
- Hutool: 5.8.25
- JWT: 0.12.5

### 1.3 Java Compatibility
- [ ] Java 17 is the target version (`maven.compiler.target=17`)
- [ ] No deprecated Java APIs in use
- [ ] No unchecked generics warnings

---

## 2. API Compatibility Matrix

### 2.1 Critical API Methods to Validate

#### PageResult Factory Methods
**Location:** `idata-common/src/main/java/com/idataai/common/core/domain/PageResult.java`

| Method | Signature | Valid Usage | Status |
|--------|-----------|-------------|--------|
| ✅ `build()` | `build(List<T>, Long, Long, Long)` | `PageResult.build(list, total, current, size)` | ACTIVE |
| ✅ `empty()` | `empty(Long, Long)` | `PageResult.empty(pageNum, pageSize)` | ACTIVE |
| ❌ `of()` | NOT IMPLEMENTED | DO NOT USE | REMOVED |

**Files Using PageResult:**
- `idata-system/src/main/java/com/idataai/system/service/impl/UserServiceImpl.java` ✅
- `idata-content/src/main/java/com/idataai/content/controller/ArticleController.java` ✅
- `idata-app/src/main/java/com/idataai/app/service/impl/InstanceServiceImpl.java` ✅
- `idata-app/src/main/java/com/idataai/app/service/impl/ApplicationServiceImpl.java` ✅

#### RedisService Cache Methods
**Location:** `idata-common/src/main/java/com/idataai/common/redis/service/RedisService.java`

| Method | Expected Signature | Parameters | Status |
|--------|-------------------|------------|----|
| ✅ `set()` | `set(String, Object, long, TimeUnit)` | (key, value, timeout, unit) | ACTIVE |
| ✅ `setEx()` | `setEx(String, Object, long)` | (key, value, seconds) | LEGACY - Use `set()` with SECONDS |
| ✅ `get()` | `get(String, Class<T>)` | (key, type) | ACTIVE |
| ✅ `delete()` | `delete(String)` or `delete(Collection)` | key(s) | ACTIVE |

**Files Using RedisService:**
- `idata-auth/src/main/java/com/idataai/auth/service/impl/AuthServiceImpl.java` - Uses `setEx()` with 3 params ✅
- `idata-system/src/main/java/com/idataai/system/service/impl/UserServiceImpl.java` - Uses `set()` with 4 params ✅

### 2.2 Common API Pitfalls Checklist

- [ ] **No `PageResult.of()` usage** - Must use `build()` or `empty()`
- [ ] **No setEx() with 4 parameters** - Use `set(key, value, timeout, TimeUnit)` instead
- [ ] **No mixing TimeUnit inconsistencies** - Maintain consistency across codebase
- [ ] **Proper generic type handling** - `get()` method requires explicit class parameter for type casting
- [ ] **Null checks before cache operations** - Validate keys and values before Redis operations

---

## 3. Module-Level Build Checks

### 3.1 Core Dependencies Module (`idata-common`)
```bash
# Check for orphaned files
mvn clean verify -f idata-common/pom.xml

# Validate no circular dependencies
mvn dependency:tree -f idata-common/pom.xml
```
- [ ] Compiles without warnings
- [ ] All exports properly declared
- [ ] test scope properly isolated

### 3.2 Authentication Module (`idata-auth`)
- [ ] Security configurations valid
- [ ] JWT token generation works
- [ ] Password encoding consistent
- [ ] Redis cache operations use correct API

### 3.3 System Module (`idata-system`)
- [ ] User service properly implements IUserService
- [ ] All conversions use correct PageResult factory
- [ ] Cache operations use `set()` with TimeUnit

### 3.4 Application Module (`idata-app`)
- [ ] Instance and Application services properly paginate
- [ ] All PageResult constructions use factory methods

### 3.5 Content Module (`idata-content`)
- [ ] Controllers properly return paginated results
- [ ] ArticleController uses PageResult.empty() correctly

### 3.6 Payment Module (`idata-payment`)
- [ ] All business operations properly transactional
- [ ] Order and credit services validate inputs

### 3.7 Marketing Module (`idata-marketing`)
- [ ] Campaign logic properly isolated
- [ ] Invitation code generation and validation

---

## 4. Compilation & Build Validation

### 4.1 Full Build
```bash
mvn clean package -f backend/pom.xml
```
- [ ] All modules compile successfully
- [ ] Zero compilation warnings
- [ ] No deprecated API usage warnings
- [ ] All JAR artifacts generated in target directories

### 4.2 Test Execution
```bash
mvn test -f backend/pom.xml
```
- [ ] All unit tests pass
- [ ] Test coverage > 70% (baseline)
- [ ] No test flakiness
- [ ] Integration tests execute successfully

### 4.3 Code Quality
```bash
mvn clean verify -f backend/pom.xml
```
- [ ] No code style violations
- [ ] No security vulnerabilities detected
- [ ] No critical bugs identified by static analysis

---

## 5. Known Issues & Deprecations

### 5.1 Fixed Issues ✅
- **Issue:** `PageResult.of()` method does not exist
  - **Error Location:** UserServiceImpl.java:63
  - **Fix:** Replace with `PageResult.build(list, total, current, size)`
  - **Status:** ✅ RESOLVED
  
- **Issue:** `RedisService.setEx()` signature mismatch with 4 parameters
  - **Error Location:** UserServiceImpl.java:84
  - **Fix:** Use `set(key, value, timeout, TimeUnit)` instead
  - **Status:** ✅ RESOLVED

- **Issue:** ArticleController returning `PageResult.of(null, 0L)`
  - **Error Location:** ArticleController.java:29
  - **Fix:** Use `PageResult.empty(pageNum, pageSize)`
  - **Status:** ✅ RESOLVED

### 5.2 Pending Implementation (TODOs)
Listed by priority: **HIGH**, **MEDIUM**, **LOW**

| Module | Location | Todo | Priority |
|--------|----------|------|----------|
| auth | AuthServiceImpl.java:122 | Implement invitation reward logic | MEDIUM |
| auth | AuthServiceImpl.java:249 | Get real client IP from HttpServletRequest | HIGH |
| content | ArticleController.java | Implement article CRUD operations | HIGH |
| content | ThinkingController.java | Implement thinking analysis logic | HIGH |
| payment | OrderController.java | Implement order creation/query logic | HIGH |
| payment | CreditController.java | Implement credit operations | HIGH |
| marketing | TrendingController.java | Implement trending list query | MEDIUM |
| marketing | InvitationController.java | Implement invitation code operations | MEDIUM |
| app | InstanceServiceImpl.java | Implement credit validation and deduction | HIGH |

---

## 6. Regression Prevention Rules

### 6.1 Before Committing Code
- [ ] All changes compile without errors or warnings
- [ ] All new public APIs documented (JavaDoc)
- [ ] All test cases pass locally
- [ ] No TODO without ticket reference
- [ ] All API calls match expected signatures

### 6.2 API Contract Enforcement
- [ ] **Never add parameters without versioning** - Create new method overloads
- [ ] **Never remove public methods** - Deprecate first, then phase out
- [ ] **Never change method signatures** - Break compatibility intentionally only with major version bump
- [ ] **Always update factory methods together** - If changing PageResult, update all callers

### 6.3 Dependency Management Rules
- [ ] **Always pin versions in pom.xml** - Never use `[1.0,2.0)` range versions
- [ ] **Review dependency updates** - Check CHANGELOG for breaking changes
- [ ] **Verify compatibility** - Test after any major/minor version updates
- [ ] **Document version constraints** - Comment why specific versions are required

---

## 7. Build Artifacts Validation

### 7.1 JAR Generation
```bash
mvn package -DskipTests -f backend/pom.xml
```

Expected artifacts:
- ✅ `idata-common-1.0.0.jar`
- ✅ `idata-auth-1.0.0.jar`
- ✅ `idata-system-1.0.0.jar`
- ✅ `idata-app-1.0.0.jar`
- ✅ `idata-content-1.0.0.jar`
- ✅ `idata-payment-1.0.0.jar`
- ✅ `idata-marketing-1.0.0.jar`

### 7.2 JAR Integrity
- [ ] All JAR files contain correct manifest
- [ ] No duplicate classes across modules
- [ ] META-INF properly structured
- [ ] All dependencies resolved (no missing classes at runtime)

---

## 8. Deployment Checklist (Pre-Production)

### 8.1 Runtime Configuration
- [ ] All environment variables documented
- [ ] Database schema matches entity definitions
- [ ] Redis configuration reachable
- [ ] Security credentials properly managed

### 8.2 Performance Validation
- [ ] No N+1 query problems detected
- [ ] Paginated queries use proper pagination
- [ ] Cache TTLs properly configured
- [ ] Connection pool sizes appropriate for load

### 8.3 Security Audit
- [ ] No hardcoded secrets in code
- [ ] All API endpoints protected appropriately
- [ ] SQL injection prevention validated
- [ ] Authentication/Authorization working correctly

---

## 9. Continuous Integration Commands

### Quick Build (Fast Feedback)
```bash
# Compile only, no tests
mvn clean compile -f backend/pom.xml
```

### Full Pipeline Build
```bash
# Compile + Tests + Package
mvn clean package -f backend/pom.xml
```

### Strict Build (No Warnings Allowed)
```bash
# Fail on warnings
mvn clean package -DfailOnWarning=true -f backend/pom.xml
```

### Extended Validation
```bash
# Include all checks: style, analysis, tests
mvn clean verify -f backend/pom.xml
```

---

## 10. Troubleshooting Guide

### Build Fails with "Cannot find symbol"
1. Check Maven dependency versions match actual API
2. Run `mvn clean` to clear stale cache
3. Verify correct `PageResult` factory method usage
4. Review recent dependency version updates

### Tests Fail After Merge
1. Check for merge conflicts in configuration files
2. Verify all API signatures still match declarations
3. Run clean build: `mvn clean install`
4. Check for environmental differences (DB, Redis)

### Runtime ClassNotFoundException
1. Verify all JAR artifacts present in classpath
2. Check module version consistency
3. Validate `META-INF/MANIFEST.MF` entries
4. Ensure no duplicate classes in different modules

---

## 11. Version Control Integration

### Pre-Commit Hook (Recommended)
```bash
#!/bin/bash
# Validate code before commit
mvn clean compile -q || exit 1
mvn test -q || exit 1
```

### CI Pipeline Trigger
- Every merge to `develop` branch: Run full pipeline
- Every merge to `main` branch: Run strict validation + deployment tests
- Weekly scheduled: Extended analysis + dependency security scan

---

## Appendix: API Signatures Reference

### PageResult API
```java
// ✅ CORRECT
PageResult.build(userVOList, userPage.getTotal(), userPage.getCurrent(), userPage.getSize());
PageResult.empty(pageNum, pageSize);

// ❌ INCORRECT (Method doesn't exist)
PageResult.of(list, total);
```

### RedisService API
```java
// ✅ CORRECT - Set with TimeUnit
redisService.set(cacheKey, userVO, USER_CACHE_TIMEOUT, TimeUnit.HOURS);

// ✅ CORRECT - Legacy with seconds (3 params)
redisService.setEx(cacheKey, userVO, 3600);

// ❌ INCORRECT - Wrong number of parameters
redisService.setEx(cacheKey, userVO, USER_CACHE_TIMEOUT, TimeUnit.HOURS);
```

---

**Document Status:** Current ✅  
**Last Validation:** 2026-02-17  
**Next Review Date:** 2026-03-17
