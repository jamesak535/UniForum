import React from 'react';
import { Link } from 'react-router-dom';
import LoginForm from '../components/auth/LoginForm';

const LoginPage = () => {
  return (
    <div className="max-w-md mx-auto">
      <LoginForm />
      <p className="text-center mt-4 text-gray-600">
        Don't have an account?{' '}
        <Link to="/register" className="text-primary-600 hover:underline">
          Register here
        </Link>
      </p>
    </div>
  );
};

export default LoginPage;