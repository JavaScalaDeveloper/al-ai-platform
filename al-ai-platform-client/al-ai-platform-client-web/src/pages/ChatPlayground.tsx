import React, { useState } from 'react';
import { Button, Card, Input, List, Select, Space, Typography, message } from 'antd';
import { adminApi } from '../api/admin';

const { TextArea } = Input;

interface Msg { role: string; content: string }

const ChatPlayground: React.FC = () => {
  const [sessionId, setSessionId] = useState<number | undefined>();
  const [modelCode, setModelCode] = useState<string>();
  const [models, setModels] = useState<any[]>([]);
  const [input, setInput] = useState('');
  const [msgs, setMsgs] = useState<Msg[]>([]);
  const [loading, setLoading] = useState(false);

  React.useEffect(() => {
    adminApi.modelList({ pageNum: 1, pageSize: 100, status: 1 }).then((res) => {
      setModels(res.data?.list || []);
    });
  }, []);

  const send = async () => {
    if (!input.trim()) return;
    const userText = input.trim();
    setInput('');
    setMsgs((prev) => [...prev, { role: 'USER', content: userText }]);
    setLoading(true);
    try {
      const res = await adminApi.chatCompletion({
        sessionId,
        modelCode,
        message: userText,
      });
      setSessionId(res.data?.sessionId);
      setMsgs((prev) => [...prev, { role: 'ASSISTANT', content: res.data?.reply || '' }]);
    } catch (e) {
      message.error('调用失败，请检查模型服务与配置');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <Typography.Title level={4}>对话试验场</Typography.Title>
      <Space style={{ marginBottom: 16 }} wrap>
        <Select
          allowClear
          placeholder="选择模型（可空=默认）"
          style={{ width: 280 }}
          value={modelCode}
          onChange={setModelCode}
          options={models.map((m) => ({ value: m.modelCode, label: `${m.modelName} (${m.modelCode})` }))}
        />
        <Button onClick={() => { setSessionId(undefined); setMsgs([]); }}>新会话</Button>
        {sessionId ? <Typography.Text type="secondary">sessionId={sessionId}</Typography.Text> : null}
      </Space>
      <Card style={{ minHeight: 360, marginBottom: 16 }}>
        <List
          dataSource={msgs}
          locale={{ emptyText: '发送一条消息开始对话（需配置可用 LLM）' }}
          renderItem={(item) => (
            <List.Item>
              <div style={{ width: '100%' }}>
                <Typography.Text strong>{item.role === 'USER' ? '你' : '助手'}</Typography.Text>
                <div style={{ whiteSpace: 'pre-wrap', marginTop: 4 }}>{item.content}</div>
              </div>
            </List.Item>
          )}
        />
      </Card>
      <Space.Compact style={{ width: '100%' }}>
        <TextArea rows={3} value={input} onChange={(e) => setInput(e.target.value)}
          onPressEnter={(e) => { if (!e.shiftKey) { e.preventDefault(); send(); } }}
          placeholder="输入消息，Enter 发送，Shift+Enter 换行" />
      </Space.Compact>
      <Button type="primary" style={{ marginTop: 12 }} loading={loading} onClick={send}>发送</Button>
    </div>
  );
};

export default ChatPlayground;
