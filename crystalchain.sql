select department.name, count(employee.id) as employees_in_dept 
  from department left outer join employee on department.id = employee.dept_id 
  group by department.name order by employees_in_dept desc, department.name desc;