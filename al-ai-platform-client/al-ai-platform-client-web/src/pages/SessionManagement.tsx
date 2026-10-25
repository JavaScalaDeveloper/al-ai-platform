import React, { useEffect, useState } from 'react';
import { Button, Space, Table, message } from 'antd';
import { adminApi } from '../api/admin';

const SessionManagement: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<any[]>([]);
  const [total, setTotal] = useState(0);
  const [pageNum, setPageNum] = useState(1);

  const load = async (page = pageNum) => {
    setLoading(true);
    try {
      const res = await adminApi.chatSessionList({ pageNum: page, pageSize: 10 });
      setData(res.data?.list || []);
      setTotal(res.data?.total || 0);
      setPageNum(page);
    } finally { setLoading(false); }
  };

  useEffect(() => { load(1); }, []);

  return (
    <div>
      <Space style={{ marginBottom: 16 }}>
        <Button onClick={() => load(pageNum)}>刷新</Button>
      </Space>
      <Table rowKey="id" loading={loading} dataSource={data}
        pagination={{ current: pageNum, total, onChange: load }}
        columns={[
          { title: '会话编码', dataIndex: 'sessionCode' },
          { title: '标题', dataIndex: 'title' },
          { title: '模型', dataIndex: 'modelCode' },
          { title: '消息数', dataIndex: 'messageCount' },
          { title: '创建时间', dataIndex: 'createTime' },
          { title: '操作', render: (_, row) => (
            <Button type="link" danger onClick={async () => {
              await adminApi.chatSessionDelete({ id: row.id });
              message.success('已删除');
              load(pageNum);
            }}>删除</Button>
          )},
        ]}
      />
    </div>
  );
};

export default SessionManagement;
