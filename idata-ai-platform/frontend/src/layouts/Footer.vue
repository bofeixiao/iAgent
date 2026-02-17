<template>
  <div class="footer-container">
    <div class="footer-content">
      <div class="footer-section">
        <h4>关于我们</h4>
        <p>iData AI是专业的AI创作平台</p>
        <p>致力于为创作者提供最优质的AI工具</p>
      </div>

      <div class="footer-section">
        <h4>快速链接</h4>
        <ul>
          <li><router-link to="/applications">全部应用</router-link></li>
          <li><router-link to="/articles">精华内容</router-link></li>
          <li><router-link to="/thinking">思维分析</router-link></li>
        </ul>
      </div>

      <div class="footer-section">
        <h4>联系我们</h4>
        <p>邮箱: contact@idataai.com</p>
        <p>电话: 400-123-4567</p>
      </div>
    </div>

    <div class="footer-bottom">
      <p>© 2024 iDataAI团队. 保留所有权利.</p>
    </div>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped lang="scss">
.footer-container {
  background: #2c3e50;
  color: #fff;
  padding: 24px 48px;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 48px;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-section {
  h4 {
    font-size: 16px;
    margin-bottom: 12px;
    color: #fff;
  }

  p {
    font-size: 14px;
    color: #bdc3c7;
    margin: 8px 0;
  }

  ul {
    list-style: none;
    padding: 0;
    margin: 0;

    li {
      margin: 8px 0;

      a {
        color: #bdc3c7;
        text-decoration: none;
        transition: color 0.3s;

        &:hover {
          color: #fff;
        }
      }
    }
  }
}

.footer-bottom {
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #34495e;
  text-align: center;

  p {
    font-size: 12px;
    color: #95a5a6;
    margin: 0;
  }
}
</style>
