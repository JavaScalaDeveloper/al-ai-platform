import React, { useEffect, useState } from 'react';
import { Button, Form, Input, Modal, Space, Table, message } from 'antd';
import { adminApi } from '../api/admin';

const PromptManagement: React.FC = () => {
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
      const res = await adminApi.promptList({ pageNum: page, pageSize: 10 });
      setData(res.data?.list || []);
      setTotal(res.data?.total || 0);
      setPageNum(page);
    } finally { setLoading(false); }
  };

  useEffect(() => { load(1); }, []);

  const onSave = async () => {
    const values = await form.validateFields();
    if (editing?.id) await adminApi.promptUpdate({ ...values, id: editing.id });
    else await adminApi.promptCreate(values);
    message.success('保存成功');
    setOpen(false);
    load(pageNum);
  };

  return (
    <div>
      <Space style={{ marginBottom: 16 }}>
        <Button type="primary" onClick={() => { setEditing(null); form.resetFields(); setOpen(true); }}>新建模板</Button>
        <Button onClick={() => load(pageNum)}>刷新</Button>
      </Space>
      <Table rowKey="id" loading={loading} dataSource={data}
        pagination={{ current: pageNum, total, onChange: load }}
        columns={[
          { title: '编码', dataIndex: 'promptCode' },
          { title: '名称', dataIndex: 'promptName' },
          { title: '分类', dataIndex: 'category' },
          { title: '状态', dataIndex: 'status', render: (v) => (v === 1 ? '启用' : '停用') },
          { title: '操作', render: (_, row) => (
            <Space>
              <Button type="link" onClick={() => { setEditing(row); form.setFieldsValue(row); setOpen(true); }}>编辑</Button>
              <Button type="link" danger onClick={async () => { await adminApi.promptDelete({ id: row.id }); message.success('已删除'); load(pageNum); }}>删除</Button>
            </Space>
          )},
        ]}
      />
      <Modal title={editing ? '编辑模板' : '新建模板'} open={open} onOk={onSave} onCancel={() => setOpen(false)} width={720} destroyOnClose>
        <Form form={form} layout="vertical" initialValues={{ category: 'GENERAL' }}>
          <Form.Item name="promptCode" label="编码" rules={[{ required: true }]}><Input disabled={!!editing} /></Form.Item>
          <Form.Item name="promptName" label="名称" rules={[{ required: true }]}><Input /></Form.Item>
          <Form.Item name="category" label="分类"><Input /></Form.Item>
          <Form.Item name="systemPrompt" label="系统提示词"><Input.TextArea rows={4} /></Form.Item>
          <Form.Item name="userPrompt" label="用户提示词模板"><Input.TextArea rows={4} /></Form.Item>
          <Form.Item name="description" label="描述"><Input /></Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default PromptManagement;
