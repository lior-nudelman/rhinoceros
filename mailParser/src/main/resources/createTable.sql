CREATE TABLE public.user_data (
  id INTEGER NOT NULL,
  amount REAL,
  transaction_date BIGINT,
  transaction_from VARCHAR(255),
  transaction_type VARCHAR(255),
  user_id VARCHAR(255),
  CONSTRAINT user_data_pkey PRIMARY KEY(id),
  CONSTRAINT unique_data UNIQUE(amount,transaction_date,transaction_from)
);
