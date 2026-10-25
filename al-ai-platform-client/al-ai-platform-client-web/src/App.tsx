import React from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import Layout from './pages/Layout';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import ModelManagement from './pages/ModelManagement';
import KnowledgeManagement from './pages/KnowledgeManagement';
import PromptManagement from './pages/PromptManagement';
import ChatPlayground from './pages/ChatPlayground';
import SessionManagement from './pages/SessionManagement';

function App() {
  return (
    <ConfigProvider locale={zhCN} theme={{ token: { colorPrimary: '#1677ff' } }}>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Layout />}>
            <Route index element={<Navigate to="/chat/playground" replace />} />
            <Route path="chat/playground" element={<ChatPlayground />} />
            <Route path="chat/sessions" element={<SessionManagement />} />
            <Route path="knowledge/bases" element={<KnowledgeManagement />} />
            <Route path="knowledge/prompts" element={<PromptManagement />} />
            <Route path="model/configs" element={<ModelManagement />} />
            <Route path="ops/dashboard" element={<Dashboard />} />
          </Route>
          <Route path="*" element={<Navigate to="/chat/playground" replace />} />
        </Routes>
      </BrowserRouter>
    </ConfigProvider>
  );
}

export default App;
