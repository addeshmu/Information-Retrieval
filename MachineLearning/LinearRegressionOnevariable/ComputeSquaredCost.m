% Squared COST function for linear regression
function J = ComputeSquaredCost(x,y,theta)
  m = length(y); % get the number of training samples.
  J = 0 ;% variable to hold the cost
  cost = 0 ;
  for i = 1:m,
    cost = cost + (theta(1,1)*x(i,1) + theta(2,1)*x(i,2) - y(i))^2;
  end  
  J = cost/(2*m);
end


