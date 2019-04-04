const fs = require('fs');
const faker = require('faker');
const { name, random, lorem } = faker;

const state = ['Active', 'Retired', 'Left', 'Deleted'];
const passwords = [
  'sha1:64000:18:vsdkdIdSRLgw14wBD3qh8ULxlsqwYXSw:NsSIeLaUw6kMGIxX5LYpM9PE',
  'sha1:64000:18:qKTMQWOUyX1GchgMncwpmUhMSvrLzFzq:GFRGr/5k6u+VpOpC6EdlmTmo',
  'sha1:64000:18:yAyImS1UyT+x0Cn7vy7+ja1j+1px37sK:5oSm7EQD60CYsPVSBlPD/kVL',
  'sha1:64000:18:l7wkYTrY4fRXpwccvJKVRhFiI12DZ0Hq:wgiyZLRQS66wnpQligAt00Ij',
  'sha1:64000:18:22k1u/CVnB5wOZ0MNrQf5041vjVHnJU8:kGcKb1007/UWJ+3rUtCYzOon',
  'sha1:64000:18:huFiHqRJ2y1PLdzugxpmf+lu7f+RD6c/:MMrpck3a6czwdCpyVGfz+Ox7',
  'sha1:64000:18:xNjLi67QuyjrwebqzZxXJ934Swb7tHl1:y3ZBUjLSmygcheszcVEWSCJ9',
  'sha1:64000:18:D5jxzwR/tU2BAYGH9mL7PVmB3aoJadE8:Zp+c6cIH90BQ7eo/PeC+KNrN',
  'sha1:64000:18:7c7RWnisX2TLUWiFOQSMqSTVrplr6Fpv:KeoRY0wdy0YEzKOoPgW5SrYT'
];
let result = '';

for (let i = 16; i <= 2000; i++) {
  const firstName = sanitizeName(name.firstName());
  const lastName = sanitizeName(name.lastName());

  let username = `${(
    firstName.charAt(0) + lastName
  ).toLowerCase()}${random.number()}`;
  if (username.includes("'")) {
    let pos = username.indexOf("'");
    username = username.substring(0, pos - 1) + username.substring(pos + 1);
  }

  const randomPassword = passwords[Math.floor(Math.random() * passwords.length)];
  const randomState = state[Math.floor(Math.random() * state.length)];
  const randomSuperEmpNo = Math.floor(Math.random() * 15) + 1;
  const randomApproEmpNo = Math.floor(Math.random() * 100) + 1;
  const insertStatement = `INSERT INTO APMS.Employee VALUES ('${i}', '${firstName}', '${lastName}', '${username}', '${randomPassword}', '${randomSuperEmpNo}', '${randomApproEmpNo}', '${randomState}', '${lorem.sentence()}');\n`;
  result += insertStatement;
}

fs.writeFile('./output/Employee.sql', result, err => {
  if (err) {
    return console.log(err);
  }

  console.log('Generated Employee seed data.');
});

function sanitizeName(name) {
  if (name.charAt(1) === "'") {
    return name.charAt(0) + "\\'" + name.substring(2);
  }

  return name;
}
