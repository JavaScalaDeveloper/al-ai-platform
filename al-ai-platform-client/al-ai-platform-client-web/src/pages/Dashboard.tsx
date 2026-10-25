import React, { useEffect, useState } from 'react';
import { Card, Col, Row, Statistic, Typography } from 'antd';
import { adminApi } from '../api/admin';

const Dashboard: React.FC = () => {
  const [data, setData] = useState<any>({});

  useEffect(() => {
    adminApi.dashboard().then((res) => setData(res.data || {}));
  }, []);

  return (
    <div>
      <Typography.Title level={4}>运维看板</Typography.Title>
      <Row gutter={16}>
        <Col span={8}><Card><Statistic title="产品" value={data.product || '-'} /></Card></Col>
        <Col span={8}><Card><Statistic title="状态" value={data.status || '-'} /></Card></Col>
        <Col span={8}><Card><Statistic title="模块数" value={(data.modules || []).length} /></Card></Col>
      </Row>
    </div>
  );
};

export default Dashboard;
