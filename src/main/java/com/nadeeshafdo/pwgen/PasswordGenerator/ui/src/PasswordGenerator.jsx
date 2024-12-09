// src/PasswordGenerator.jsx
import React, { useState } from 'react';
import axios from 'axios';

const PasswordGenerator = () => {
  const [length, setLength] = useState(12);
  const [includeUppercase, setIncludeUppercase] = useState(true);
  const [includeLowercase, setIncludeLowercase] = useState(true);
  const [includeDigits, setIncludeDigits] = useState(true);
  const [includeSpecialCharacters, setIncludeSpecialCharacters] = useState(true);
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const generatePassword = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/generate-password', {
        params: {
          length,
          includeUppercase,
          includeLowercase,
          includeDigits,
          includeSpecialCharacters,
        },
      });
      setPassword(response.data);
      setError('');
    } catch (err) {
      setError(err.response ? err.response.data : 'An error occurred');
      setPassword('');
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <h2>Password Generator</h2>
      <div>
        <label>
          Length:
          <input
            type="number"
            value={length}
            onChange={(e) => setLength(e.target.value)}
            min="1"
            style={{ marginLeft: '10px' }}
          />
        </label>
      </div>
      <div style={{ marginTop: '10px' }}>
        <label>
          <input
            type="checkbox"
            checked={includeUppercase}
            onChange={(e) => setIncludeUppercase(e.target.checked)}
          />
          Include Uppercase
        </label>
      </div>
      <div>
        <label>
          <input
            type="checkbox"
            checked={includeLowercase}
            onChange={(e) => setIncludeLowercase(e.target.checked)}
          />
          Include Lowercase
        </label>
      </div>
      <div>
        <label>
          <input
            type="checkbox"
            checked={includeDigits}
            onChange={(e) => setIncludeDigits(e.target.checked)}
          />
          Include Digits
        </label>
      </div>
      <div>
        <label>
          <input
            type="checkbox"
            checked={includeSpecialCharacters}
            onChange={(e) => setIncludeSpecialCharacters(e.target.checked)}
          />
          Include Special Characters
        </label>
      </div>
      <button onClick={generatePassword} style={{ marginTop: '20px', padding: '10px 20px' }}>
        Generate Password
      </button>
      {error && <p style={{ color: 'red', marginTop: '10px' }}>{error}</p>}
      {password && <p style={{ marginTop: '10px' }}>Generated Password: {password}</p>}
    </div>
  );
};

export default PasswordGenerator;