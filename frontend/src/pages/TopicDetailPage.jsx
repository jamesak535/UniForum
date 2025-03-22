import React from 'react';
import { useParams } from 'react-router-dom';

const TopicDetailPage = () => {
  const { id } = useParams();
  
  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Topic Details</h1>
      <p>Viewing topic with ID: {id}</p>
    </div>
  );
};

export default TopicDetailPage;