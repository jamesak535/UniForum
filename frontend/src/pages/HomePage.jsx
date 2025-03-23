// import React from 'react';
// import { Link } from 'react-router-dom';
// import { useAuth } from '../context/AuthContext';

// const HomePage = () => {
//   const { currentUser } = useAuth();

//   return (
//     <div className="max-w-3xl mx-auto">
//       <div className="text-center mb-12">
//         <h1 className="text-4xl font-bold text-gray-900 mb-4">Welcome to UniForum</h1>
//         <p className="text-xl text-gray-600">
//           A place for university students to connect and discuss various topics
//         </p>
//         {!currentUser && (
//           <div className="mt-8 space-x-4">
//             <Link
//               to="/register"
//               className="px-6 py-3 bg-primary-600 text-white font-medium rounded-md hover:bg-primary-700"
//             >
//               Join Now
//             </Link>
//             <Link
//               to="/login"
//               className="px-6 py-3 bg-white text-primary-600 font-medium rounded-md border border-primary-600 hover:bg-primary-50"
//             >
//               Sign In
//             </Link>
//           </div>
//         )}
//       </div>

//       <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8">
//         <div className="bg-white p-6 rounded-lg shadow-md">
//           <h2 className="text-2xl font-bold text-gray-800 mb-4">Latest Topics</h2>
//           <Link
//             to="/topics"
//             className="text-primary-600 hover:underline block mt-4"
//           >
//             Browse all topics →
//           </Link>
//         </div>

//         <div className="bg-white p-6 rounded-lg shadow-md">
//           <h2 className="text-2xl font-bold text-gray-800 mb-4">Join the Discussion</h2>
//           <p className="text-gray-600 mb-4">
//             Start a new topic or contribute to existing discussions with students from your university.
//           </p>
//           <Link
//             to={currentUser ? "/create-topic" : "/login"}
//             className="text-primary-600 hover:underline"
//           >
//             {currentUser ? "Create a new topic →" : "Sign in to participate →"}
//           </Link>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default HomePage;


import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const HomePage = () => {
  const { currentUser } = useAuth();
  const [universities, setUniversities] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // In a real application, you would fetch this from your API
    // For now, we'll use mock data
    const mockUniversities = [
      { id: 1, name: 'University of California, Berkeley', abbreviation: 'UC Berkeley', members: 1240, topics: 87 },
      { id: 2, name: 'Stanford University', abbreviation: 'Stanford', members: 980, topics: 65 },
      { id: 3, name: 'Massachusetts Institute of Technology', abbreviation: 'MIT', members: 1450, topics: 92 },
      { id: 4, name: 'Harvard University', abbreviation: 'Harvard', members: 1120, topics: 78 },
      { id: 5, name: 'University of Michigan', abbreviation: 'UMich', members: 890, topics: 56 },
      { id: 6, name: 'University of Washington', abbreviation: 'UW', members: 760, topics: 43 },
      { id: 7, name: 'University of Texas at Austin', abbreviation: 'UT Austin', members: 920, topics: 61 },
      { id: 8, name: 'Cornell University', abbreviation: 'Cornell', members: 850, topics: 53 },
    ];
    
    setUniversities(mockUniversities);
    setLoading(false);
  }, []);

  return (
    <div className="max-w-4xl mx-auto">
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

      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold text-gray-800">University Forums</h2>
          {currentUser && (
            <Link
              to="/create-topic"
              className="px-4 py-2 bg-primary-600 text-white font-medium rounded-md hover:bg-primary-700"
            >
              Create New Topic
            </Link>
          )}
        </div>
        
        {loading ? (
          <div className="text-center py-8">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary-500 mx-auto"></div>
            <p className="mt-4 text-gray-600">Loading university forums...</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {universities.map((university) => (
              <div 
                key={university.id} 
                className="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow"
              >
                <h3 className="text-xl font-bold text-gray-800 mb-1">{university.name}</h3>
                <p className="text-gray-500 text-sm mb-3">{university.abbreviation}</p>
                <div className="flex justify-between text-sm text-gray-600">
                  <span>{university.members} members</span>
                  <span>{university.topics} topics</span>
                </div>
                <Link
                  to={`/university/${university.id}`}
                  className="mt-4 text-primary-600 hover:underline inline-block"
                >
                  Browse forum →
                </Link>
              </div>
            ))}
          </div>
        )}
      </div>

      <div className="bg-primary-50 rounded-lg p-6 border border-primary-100">
        <h2 className="text-xl font-bold text-primary-800 mb-2">Looking for a specific university?</h2>
        <p className="text-primary-700 mb-4">
          If you don't see your university listed above, you can search for it or request to add it.
        </p>
        <div className="flex flex-col sm:flex-row gap-4">
          <Link
            to="/universities"
            className="px-4 py-2 bg-white text-primary-600 font-medium rounded-md border border-primary-300 hover:bg-primary-50 text-center"
          >
            View All Universities
          </Link>
          {currentUser && (
            <Link
              to="/request-university"
              className="px-4 py-2 bg-primary-600 text-white font-medium rounded-md hover:bg-primary-700 text-center"
            >
              Request New University
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default HomePage;