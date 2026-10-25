import React, { useState } from 'react';
import { Button, Card, Form, Input, message } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { adminApi } from '../api/admin';
import { encryptLoginPassword } from '../utils/passwordCrypto';

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const onFinish = async (values: { username: string; password: string }) => {
    setLoading(true);
    try {
      const encryptedPassword = await encryptLoginPassword(values.password);
      const res = await adminApi.login({
        username: values.username,
        password: encryptedPassword,
      });
      const data = res.data!;
      localStorage.setItem('adminToken', data.token);
      localStorage.setItem('adminUser', data.username);
      message.success('登录成功');
      navigate('/');
    } catch (e: any) {
      if (e?.message && !e?.notified) {
        message.error(e.message);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{
      height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center',
      background: 'linear-gradient(135deg, #1d39c4 0%, #10239e 45%, #061178 100%)',
    }}>
      <Card style={{ width: 400, boxShadow: '0 8px 24px rgba(0,0,0,0.2)' }} bordered={false}>
        <div style={{ textAlign: 'center', marginBottom: 28 }}>
          <h1 style={{ fontSize: 26, margin: 0, color: '#1677ff' }}>AL AI Platform</h1>
          <p style={{ color: '#999', marginTop: 8 }}>管理后台登录</p>
        </div>
        <Form onFinish={onFinish} initialValues={{ username: 'superadmin', password: 'SuperAdmin@8282' }}>
          <Form.Item name="username" rules={[{ required: true, message: '请输入用户名' }]}>
            <Input prefix={<UserOutlined />} placeholder="用户名" size="large" />
          </Form.Item>
          <Form.Item name="password" rules={[{ required: true, message: '请输入密码' }]}>
            <Input.Password prefix={<LockOutlined />} placeholder="密码" size="large" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" size="large" block loading={loading}>登录</Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default Login;
