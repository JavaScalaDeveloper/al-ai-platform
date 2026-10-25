import React, { useEffect, useMemo, useState } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { Layout as AntLayout, Menu } from 'antd';
import type { MenuProps } from 'antd';
import {
  ApiOutlined,
  AppstoreOutlined,
  BookOutlined,
  CloudServerOutlined,
  DashboardOutlined,
  FormOutlined,
  HistoryOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  MessageOutlined,
} from '@ant-design/icons';

const { Header, Sider, Content, Footer } = AntLayout;

/**
 * 一级：平台能力
 * 二级：原产品卡片（对话 / 知识库 / 模型 / 运维）
 * 三级：卡片内具体功能页
 */
const PLATFORM_MENU: MenuProps['items'] = [
  {
    key: 'platform',
    icon: <AppstoreOutlined />,
    label: '平台能力',
    children: [
      {
        key: 'chat',
        icon: <MessageOutlined />,
        label: '对话与 Agent',
        children: [
          { key: '/chat/playground', icon: <MessageOutlined />, label: '对话试验场' },
          { key: '/chat/sessions', icon: <HistoryOutlined />, label: '会话列表' },
        ],
      },
      {
        key: 'knowledge',
        icon: <BookOutlined />,
        label: '知识库',
        children: [
          { key: '/knowledge/bases', icon: <BookOutlined />, label: '知识库管理' },
          { key: '/knowledge/prompts', icon: <FormOutlined />, label: 'Prompt 模板' },
        ],
      },
      {
        key: 'model',
        icon: <ApiOutlined />,
        label: '模型中心',
        children: [
          { key: '/model/configs', icon: <ApiOutlined />, label: '模型配置' },
        ],
      },
      {
        key: 'ops',
        icon: <CloudServerOutlined />,
        label: '运维看板',
        children: [
          { key: '/ops/dashboard', icon: <DashboardOutlined />, label: '控制台' },
        ],
      },
    ],
  },
];

function openKeysForPath(pathname: string): string[] {
  const keys = ['platform'];
  if (pathname.startsWith('/chat')) keys.push('chat');
  else if (pathname.startsWith('/knowledge')) keys.push('knowledge');
  else if (pathname.startsWith('/model')) keys.push('model');
  else if (pathname.startsWith('/ops')) keys.push('ops');
  return keys;
}

const Layout: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const selectedKey = useMemo(() => location.pathname, [location.pathname]);
  const [openKeys, setOpenKeys] = useState<string[]>(() => openKeysForPath(location.pathname));

  useEffect(() => {
    setOpenKeys(openKeysForPath(location.pathname));
  }, [location.pathname]);

  useEffect(() => {
    if (!localStorage.getItem('adminToken')) {
      navigate('/login', { replace: true });
    }
  }, [navigate]);

  return (
    <AntLayout style={{ minHeight: '100vh' }}>
      <Sider trigger={null} collapsible collapsed={collapsed} theme="dark" width={232}>
        <div
          style={{
            height: 64,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            color: '#fff',
            fontWeight: 'bold',
            borderBottom: '1px solid rgba(255,255,255,0.1)',
            cursor: 'pointer',
          }}
          onClick={() => navigate('/chat/playground')}
        >
          {collapsed ? 'AL' : 'AL AI Platform'}
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={[selectedKey]}
          openKeys={collapsed ? [] : openKeys}
          onOpenChange={(keys) => setOpenKeys(keys as string[])}
          items={PLATFORM_MENU}
          onClick={({ key }) => {
            if (String(key).startsWith('/')) {
              navigate(String(key));
            }
          }}
        />
      </Sider>
      <AntLayout>
        <Header
          style={{
            padding: '0 24px',
            background: '#fff',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            boxShadow: '0 1px 4px rgba(0,21,41,.08)',
          }}
        >
          {React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
            style: { fontSize: 18, cursor: 'pointer' },
            onClick: () => setCollapsed(!collapsed),
          })}
          <div style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
            <span style={{ color: '#666' }}>{localStorage.getItem('adminUser') || '管理员'}</span>
            <LogoutOutlined
              style={{ cursor: 'pointer', color: '#ff4d4f' }}
              onClick={() => {
                localStorage.removeItem('adminToken');
                localStorage.removeItem('adminUser');
                navigate('/login');
              }}
            />
          </div>
        </Header>
        <Content
          style={{
            margin: '24px 16px',
            padding: 24,
            background: '#fff',
            borderRadius: 4,
            minHeight: 280,
          }}
        >
          <Outlet />
        </Content>
        <Footer style={{ textAlign: 'center', color: '#999' }}>
          AL AI Platform ©{new Date().getFullYear()}
        </Footer>
      </AntLayout>
    </AntLayout>
  );
};

export default Layout;
