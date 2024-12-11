--
-- Subquery
--

-- 1) select절의 서브쿼리
select (select 1+1 from dual) from dual;
-- insert into t1 values(null, (select max(no)+1 from t1))

--
-- 2) from절의 서브쿼리
--
select now() as n, sysdate() as s, 3+1 as r from dual;
select a.n, a.r
from (select now() as n, sysdate() as s, 3+1 as r from dual) a;

--
-- 3) where절의 서브쿼리
--

-- 예) 현재, Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보세요.
select b.dept_no
from employees a, dept_emp b
where a.emp_no = b.emp_no
	and b.to_date = '9999-01-01'
	and concat(a.first_name, ' ', a.last_name) = 'Fai Bale';

-- d004
select a.emp_no, concat(a.first_name, ' ', a.last_name)
from employees a, dept_emp b
where a.emp_no = b.emp_no                      
	and b.to_date = '9999-01-01'
    and b.dept_no = (
		select b.dept_no
		from employees a, dept_emp b
		where a.emp_no = b.emp_no
			and b.to_date = '9999-01-01'
			and concat(a.first_name, ' ', a.last_name) = 'Fai Bale'
    );         

--
--
--
--
--
--
-- 3-1) 단일행 연산자: =, >, <, >=, <= , <>, !=
-- 실습1
-- 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력하세요.

SELECT 
    concat(first_name,' ', last_name) as Name, sa.salary
FROM
    employees ep JOIN
    (SELECT 
        emp_no, salary
    FROM
        salaries
    WHERE
        salary < (SELECT AVG(salary)
               FROM salaries
               WHERE to_date = '9999-01-01')
   AND to_date = '9999-01-01') sa ON ep.emp_no = sa.emp_no
ORDER BY sa.salary desc;

-- 실습문제2:
-- 현재, 직책별 평균 급여 중 가장 적은 평균 급여의 직책 이름과 그 평균 급여를 출력하세요
SELECT a.title, MIN(avg_salary)
FROM (SELECT tt.title, AVG(salary) as avg_salary
      FROM
         (SELECT 
            emp_no, salary
         FROM
            salaries
         WHERE
            to_date = '9999-01-01') sa
            JOIN
         (SELECT 
            emp_no, title
         FROM
            titles
         WHERE
            to_date = '9999-01-01') tt ON sa.emp_no = tt.emp_no
      GROUP BY tt.title
      ) as a;

-- 3) sol1: where절 subquery
SELECT tt.title, AVG(salary) as avg_salary
FROM
   (SELECT 
      emp_no, salary
   FROM
      salaries
   WHERE
      to_date = '9999-01-01') sa
      JOIN
   (SELECT 
      emp_no, title
   FROM
      titles
   WHERE
      to_date = '9999-01-01') tt ON sa.emp_no = tt.emp_no
GROUP BY tt.title
Having avg(salary) = (SELECT MIN(avg_salary)
                  FROM (SELECT tt.title, AVG(salary) as avg_salary
                        FROM
                           (SELECT 
                              emp_no, salary
                           FROM
                              salaries
                           WHERE
                              to_date = '9999-01-01') sa
                              JOIN
                           (SELECT 
                              emp_no, title
                           FROM
                              titles
                           WHERE
                              to_date = '9999-01-01') tt ON sa.emp_no = tt.emp_no
                        GROUP BY tt.title
                        ) as a);

-- 4) sol2 :top-k
SELECT tt.title, AVG(salary) as avg_salary
FROM
   (SELECT emp_no, salary
   FROM salaries
   WHERE to_date = '9999-01-01') sa
   JOIN
   (SELECT emp_no, title
   FROM titles
   WHERE to_date = '9999-01-01') tt ON sa.emp_no = tt.emp_no
GROUP BY tt.title
ORDER BY AVG(sa.salary) asc
limit 1;

--
--
--
--
--
--

-- 3-2) 복수행 연산자: in, not in, 비교연산자any, 비교연산자all
-- any 사용법
-- 1. =any: in
-- 2. >any, >=any: 최소값
-- 3. <any, <=any: 최대값
-- 4. <>any, !=any: not in

-- all 사용법
-- 1. =all: (x)
-- 2. >all, >=all: 최대값
-- 2. <all, <=all: 최소값
-- 4. <>all, !=all

-- 실습문제3
-- 현재, 급여가 50000 이상인 직원의 이름과 급여을 출력하세요.
-- 둘리 60000
-- 마이콜 55000

-- sol01
select a.first_name, b.salary
from employees a join salaries b using(emp_no)
where b.to_date = '9999-01-01'
	and b.salary >= 50000;

select a.first_name, b.salary
from employees a, salaries b
where a.emp_no = b.emp_no
	and b.to_date = '9999-01-01'
    and b.salary >= 50000;
    
-- sol02
select a.first_name, b.salary
from employees a, salaries b
where a.emp_no = b.emp_no
	and b.to_date = '9999-01-01'
    and (a.emp_no, b.salary) in (
		select emp_no, salary
        from salaries
        where to_date = '9999-01-01'
			and salary >= 50000
    );

-- 실습문제4
-- 현재, 각 부서별 최고급여를 받고 있는 직원의 이름, 부서이름, 급여를 출력해주세요.
-- 총무 둘리 20000
-- 개발 마이콜 50000
select c.dept_name, a.first_name, d.salary
from employees a, dept_emp b, departments c, salaries d
where a.emp_no = b.emp_no
	and a.emp_no = d.emp_no
	and b.dept_no = c.dept_no
    and d.to_date = '9999-01-01'
	and d.salary = (
		select max(salary)
		from salaries
		where to_date = '9999-01-01'
			and emp_no in (
				select emp_no
				from dept_emp
				where dept_no = b.dept_no
			)
	)
group by c.dept_no;
















