class CreateJobRequests < ActiveRecord::Migration[5.1]
  def change
    create_table :job_requests do |t|
      t.datetime	"request_date"
      t.integer		"status"
      t.timestamps
    end
  end
end
