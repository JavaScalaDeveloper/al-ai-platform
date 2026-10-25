import React, { useEffect, useState } from 'react';
import { Button, Form, Input, InputNumber, Modal, Select, Space, Table, message } from 'antd';
import { adminApi } from '../api/admin';

const ModelManagement: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<any[]>([]);
  const [total, setTotal] = useState(0);
  const [pageNum, setPageNum] = useState(1);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [form] = Form.useForm();

  const load = async (page = pageNum) => {
    setLoading(true);
    try {
      const res = await adminApi.modelList({ pageNum: page, pageSize: 10 });
      setData(res.data?.list || []);
      setTotal(res.data?.total || 0);
      setPageNum(page);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(1); }, []);

  const onSave = async () => {
    const values = await form.validateFields();
    if (editing?.id) {
      await adminApi.modelUpdate({ ...values, id: editing.id });
    } else {
      await adminApi.modelCreate({ ...values, status: 1 });
    }
    message.success('保存成功');
    setOpen(false);
    load(pageNum);
  };

  return (
    <div>
      <Space style={{ marginBottom: 16 }}>
        <Button type="primary" onClick={() => { setEditing(null); form.resetFields(); setOpen(true); }}>新建模型</Button>
        <Button onClick={() => load(pageNum)}>刷新</Button>
      </Space>
      <Table rowKey="id" loading={loading} dataSource={data}
        pagination={{ current: pageNum, total, onChange: load }}
        columns={[
          { title: '编码', dataIndex: 'modelCode' },
          { title: '名称', dataIndex: 'modelName' },
          { title: '供应商', dataIndex: 'provider' },
          { title: '模型ID', dataIndex: 'modelId' },
          { title: '默认', dataIndex: 'isDefault', render: (v) => (v === 1 ? '是' : '否') },
          { title: '状态', dataIndex: 'status', render: (v) => (v === 1 ? '启用' : '停用') },
          { title: '操作', render: (_, row) => (
            <Space>
              <Button type="link" onClick={() => { setEditing(row); form.setFieldsValue(row); setOpen(true); }}>编辑</Button>
              <Button type="link" danger onClick={async () => { await adminApi.modelDelete({ id: row.id }); message.success('已删除'); load(pageNum); }}>删除</Button>
            </Space>
          )},
        ]}
      />
      <Modal title={editing ? '编辑模型' : '新建模型'} open={open} onOk={onSave} onCancel={() => setOpen(false)} width={640} destroyOnClose>
        <Form form={form} layout="vertical" initialValues={{ provider: 'OPENAI', temperature: 0.7, maxTokens: 4096, timeoutSec: 120, isDefault: 0 }}>
          <Form.Item name="modelCode" label="模型编码" rules={[{ required: true }]}><Input disabled={!!editing} /></Form.Item>
          <Form.Item name="modelName" label="显示名" rules={[{ required: true }]}><Input /></Form.Item>
          <Form.Item name="provider" label="供应商" rules={[{ required: true }]}>
            <Select options={[{ value: 'OPENAI' }, { value: 'QWEN' }, { value: 'OLLAMA' }, { value: 'CUSTOM' }]} />
          </Form.Item>
          <Form.Item name="baseUrl" label="Base URL"><Input placeholder="http://127.0.0.1:11434/v1" /></Form.Item>
          <Form.Item name="apiKey" label="API Key"><Input.Password /></Form.Item>
          <Form.Item name="modelId" label="上游模型ID" rules={[{ required: true }]}><Input /></Form.Item>
          <Form.Item name="temperature" label="Temperature"><InputNumber style={{ width: '100%' }} step={0.1} min={0} max={2} /></Form.Item>
          <Form.Item name="maxTokens" label="Max Tokens"><InputNumber style={{ width: '100%' }} min={1} /></Form.Item>
          <Form.Item name="timeoutSec" label="超时秒"><InputNumber style={{ width: '100%' }} min={1} /></Form.Item>
          <Form.Item name="isDefault" label="默认模型">
            <Select options={[{ value: 1, label: '是' }, { value: 0, label: '否' }]} />
          </Form.Item>
          <Form.Item name="description" label="描述"><Input.TextArea rows={2} /></Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ModelManagement;
