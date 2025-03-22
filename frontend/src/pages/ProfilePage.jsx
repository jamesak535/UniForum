import React from 'react';
import { useAuth } from '../context/AuthContext';

const ProfilePage = () => {
  const { currentUser } = useAuth();
  
  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Your Profile</h1>
      {currentUser && (
        <div className="bg-white shadow-md rounded-lg p-6">
          <p><strong>Username:</strong> {currentUser.username}</p>
          <p><strong>Email:</strong> {currentUser.email}</p>
        </div>
      )}
    </div>
  );
};

export default ProfilePage;