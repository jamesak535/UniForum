import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const HomePage = () => {
  const { currentUser } = useAuth();

  return (
    <div className="max-w-3xl mx-auto">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">Welcome to UniForum</h1>
        <p className="text-xl text-gray-600">
          A place for university students to connect and discuss various topics
        </p>
        {!currentUser && (
          <div className="mt-8 space-x-4">
            <Link
              to="/register"
              className="px-6 py-3 bg-primary-600 text-white font-medium rounded-md hover:bg-primary-700"
            >
              Join Now
            </Link>
            <Link
              to="/login"
              className="px-6 py-3 bg-white text-primary-600 font-medium rounded-md border border-primary-600 hover:bg-primary-50"
            >
              Sign In
            </Link>
          </div>
        )}
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">Latest Topics</h2>
          <Link
            to="/topics"
            className="text-primary-600 hover:underline block mt-4"
          >
            Browse all topics →
          </Link>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">Join the Discussion</h2>
          <p className="text-gray-600 mb-4">
            Start a new topic or contribute to existing discussions with students from your university.
          </p>
          <Link
            to={currentUser ? "/create-topic" : "/login"}
            className="text-primary-600 hover:underline"
          >
            {currentUser ? "Create a new topic →" : "Sign in to participate →"}
          </Link>
        </div>
      </div>
    </div>
  );
};

export default HomePage;